package id.go.beacukai.scswriter.application.port.outgoing;

import id.go.beacukai.scswriter.config.TestContainerConfiguration;
import id.go.beacukai.scswriter.domain.entity.Header;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataR2dbcTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles({"test"})
@Import(TestContainerConfiguration.class)
class HeaderCommandRepositoryIntTest {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14");

    @Autowired
    private HeaderCommandRepository headerCommandRepository;

    private Header newDocumentHeader;

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://" +
                postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort() +
                "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
    }

    @BeforeEach
    void setUp() {
        var header = new Header();
        header.setKodeDokumen("20");
        header.setAsalData("W");
        header.setIdPerusahaan("1234567890");
        header.setNamaPerusahaan("DEMO PORTAL");
        header.setRoleEntitas("IMPORTIR");
        header.setNomorAju("00002012345620220222000001");
        headerCommandRepository.saveAll(List.of(header)).blockLast();

        newDocumentHeader = new Header();
        newDocumentHeader.setKodeDokumen("20");
        newDocumentHeader.setAsalData("W");
        newDocumentHeader.setRoleEntitas("IMPORTIR");
        newDocumentHeader.setIdPerusahaan("1234567890");
        newDocumentHeader.setNamaPerusahaan("PT. DEMO PORTAL");
        newDocumentHeader.setNomorAju("00002012345620220222000001");
    }

    @AfterEach
    void tearDown() {
        headerCommandRepository.deleteAll().block();
    }

    @Test
    void testHeaderCommandRepositoryExisted() {
        assertThat(headerCommandRepository).isNotNull();
    }

    @Test
    void findAll() {
        headerCommandRepository.findAll().as(StepVerifier::create)
                .consumeNextWith(header -> {
                    assertThat(header).isNotNull();
                    assertThat(header.getIdHeader()).isNotNull();
                    assertThat(header.getIdHeader()).isInstanceOf(String.class);
                    assertThat(header.getKodeDokumen()).isEqualTo("20");
                    assertThat(header.getAsalData()).isEqualTo("W");
                    assertThat(header.getNomorAju()).isEqualTo("00002012345620220222000001");
                }).verifyComplete();
    }

    @Test
    void countByIdPerusahaan() {
        headerCommandRepository.countByIdPerusahaan("1234567890").as(StepVerifier::create)
                .expectNextCount(1L)
                .verifyComplete();
    }

    @Test
    void saveForUpdate_forNonExistedRecord_shouldGetAnError() {
        var updatedHeader = new Header();
        updatedHeader.setIdHeader("ABC");
        updatedHeader.setKodeDokumen("20");
        updatedHeader.setAsalData("W");
        updatedHeader.setIdPerusahaan("1234567890");
        updatedHeader.setRoleEntitas("IMPORTIR");
        updatedHeader.setNomorAju("00002012345620220222000001");

        headerCommandRepository.save(updatedHeader).as(StepVerifier::create)
                .expectErrorMessage("Failed to update table [header]. Row with Id [ABC] does not exist.")
                .verify();
    }

    @Test
    void saveNewHeader_forDuplicatedNomorAju_shouldGetAnError() {
        headerCommandRepository.save(newDocumentHeader).as(StepVerifier::create)
                .expectErrorSatisfies(ex -> {
                    assertThat(ex.getMessage().contains(
                            "duplicate key value violates unique constraint \"header_nomor_aju_key\"")).isTrue();
                })
                .verify();
    }

    @Test
    void saveNewHeader_missingIdPerusahaan_shouldGetAnError() {
        newDocumentHeader.setIdPerusahaan(null);

        headerCommandRepository.save(newDocumentHeader).as(StepVerifier::create)
                .expectErrorSatisfies(ex -> {
                    assertThat(ex.getMessage().contains(
                            "null value in column \"id_perusahaan\" of relation \"header\" violates not-null constraint"
                    )).isTrue();
                })
                .verify();
    }

    @Test
    void saveNewHeader_missingNamaPerusahaan_shouldGetAnError() {
        newDocumentHeader.setNamaPerusahaan(null);

        headerCommandRepository.save(newDocumentHeader)
                .as(StepVerifier::create)
                .expectError(DataIntegrityViolationException.class)
                .verify();
    }

    @Test
    void saveNewHeader_missingAsalData_shouldGetAnError() {
        newDocumentHeader.setAsalData(null);

        headerCommandRepository.save(newDocumentHeader).as(StepVerifier::create)
                .expectErrorSatisfies(ex -> {
                    assertThat(ex.getMessage().contains(
                            "null value in column \"asal_data\" of relation \"header\" violates not-null constraint"
                    )).isTrue();
                })
                .verify();
    }

    @Test
    void saveNewHeader_missingKodeDokumen_shouldGetAnError() {
        newDocumentHeader.setKodeDokumen(null);

        headerCommandRepository.save(newDocumentHeader).as(StepVerifier::create)
                .expectError(DataIntegrityViolationException.class)
                .verify();
    }

    @Test
    void saveNewHeader_missingRoleEntitas_shouldGetAnError() {
        newDocumentHeader.setRoleEntitas(null);

        headerCommandRepository.save(newDocumentHeader).as(StepVerifier::create)
                .expectError(DataIntegrityViolationException.class)
                .verify();
    }

    @Test
    void saveNewHeader_missingNomorAju_shouldGetAnError() {
        newDocumentHeader.setNomorAju(null);

        headerCommandRepository.save(newDocumentHeader).as(StepVerifier::create)
                .expectError(DataIntegrityViolationException.class)
                .verify();
    }


}