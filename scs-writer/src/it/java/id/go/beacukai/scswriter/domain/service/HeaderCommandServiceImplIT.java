package id.go.beacukai.scswriter.domain.service;

import id.go.beacukai.scswriter.config.TestContainerConfiguration;
import id.go.beacukai.scswriter.domain.entity.Header;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({ "test" })
@Import(TestContainerConfiguration.class)
class HeaderCommandServiceImplIT {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14");

    @Autowired
    HeaderCommandServiceImpl headerCommandService;

    private Header header;

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://" +
                postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort() +
                "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
    }

    @Test
    @Order(1)
    void testHeaderComandServiceExisted() {
        assertThat(headerCommandService).isNotNull();
    }

    @BeforeEach
    void setUp() {
        header = new Header();
        header.setKodeDokumen("20");
        header.setAsalData("W");
        header.setIdPerusahaan("1234567890");
        header.setNamaPerusahaan("DEMO PORTAL");
        header.setRoleEntitas("IMPORTIR");
    }

    @AfterEach
    void tearDown() {
        header = null;
    }

    @Test
    @Order(2)
    void createDocumentHeader_recordIsCreated() {
        var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        var createdHeader = headerCommandService.createDocumentHeader(header).log();

        StepVerifier.create(createdHeader)
                .consumeNextWith(event -> {
                    assertThat(event).isNotNull();
                    assertThat(event.getData().getIdHeader()).isNotNull();
                    assertThat(event.getData().getIdHeader()).isInstanceOf(String.class);
                    assertThat(event.getData().getNomorAju()).isNotNull();
                    assertThat(event.getData().getNomorAju()).isEqualTo("000020123456" + currentDate + "000001");
                })
                .verifyComplete();
    }

    @Test
    @Order(3)
    void createDocumentHeader_forSecondRecordWithExistingIdPerusahaan_recordIsCreatedWithIncrementedNomorAju() {
        var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        var secondCreatedHeader = headerCommandService.createDocumentHeader(header).log();

        StepVerifier.create(secondCreatedHeader)
                .consumeNextWith(event -> {
                    assertThat(event).isNotNull();
                    assertThat(event.getData().getIdHeader()).isNotNull();
                    assertThat(event.getData().getNomorAju()).isEqualTo("000020123456" + currentDate + "000002");
                })
                .verifyComplete();
    }

    @Test
    @Order(4)
    void createDocumentHeader_forThirdRecordWithUnexistingIdPerusahaan_recordIsCreatedWithSequenceResetToOne() {
        var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        header.setIdPerusahaan("555777999");

        headerCommandService.createDocumentHeader(header).as(StepVerifier::create)
                .consumeNextWith(event -> {
                    assertThat(event).isNotNull();
                    assertThat(event.getData().getNomorAju()).isEqualTo("000020555777" + currentDate + "000001");
                })
                .verifyComplete();
    }

    @Test
    @Order(5)
    void createDocumentHeader_missingAsalData_shouldGetAnError() {
        var header = new Header();
        header.setKodeDokumen("20");
        header.setIdPerusahaan("1234567890");
        header.setNamaPerusahaan("DEMO PORTAL");
        header.setRoleEntitas("IMPORTIR");

        var response = headerCommandService.createDocumentHeader(header).log();

        StepVerifier.create(response)
                .expectErrorSatisfies(ex -> {
                    assertThat(ex.getMessage().contains(
                            "null value in column \"asal_data\" of relation \"header\" violates not-null constraint"
                    )).isTrue();
                })
                .verify();
    }

    @Test
    @Order(6)
    void createDocumentHeader_missingKodeDokumen_shouldGetAnError() {
        header.setKodeDokumen(null);

        headerCommandService.createDocumentHeader(header)
                .as(StepVerifier::create)
                .expectErrorMessage("\"kodeDokumen\" cannot be empty!")
                .verify();
    }

    @Test
    @Order(7)
    void createDocumentHeader_missingKodeDokumen_shouldGetDataIntegrityViolationException() {
        header.setKodeDokumen(null);

        headerCommandService.createDocumentHeader(header)
                .as(StepVerifier::create)
                .expectError(DataIntegrityViolationException.class)
                .verify();
    }

    @Test
    @Order(8)
    void createDocumentHeader_missingIdPerusahaan_shouldGetAnError() {
        header.setIdPerusahaan(null);

        headerCommandService.createDocumentHeader(header)
                .as(StepVerifier::create)
                .expectErrorMessage("\"idPerusahaan\" cannot be empty!")
                .verify();
    }

    @Test
    @Order(9)
    void createDocumentHeader_missingIdPerusahaan_shouldGetDataIntegrityViolationException() {
        header.setIdPerusahaan(null);

        headerCommandService.createDocumentHeader(header)
                .as(StepVerifier::create)
                .expectError(DataIntegrityViolationException.class)
                .verify();
    }

    @Test
    @Order(10)
    void updateHeader_recordIsUpdated() {
        var event = headerCommandService.createDocumentHeader(header).block();

        var updatedHeader = new Header();
        updatedHeader.setIdHeader(event.getData().getIdHeader());
        updatedHeader.setNomorAju(event.getData().getNomorAju());
        updatedHeader.setKodeDokumen(event.getData().getKodeDokumen());
        updatedHeader.setAsalData(event.getData().getAsalData());
        updatedHeader.setIdPerusahaan(event.getData().getIdPerusahaan());
        updatedHeader.setNamaPerusahaan(event.getData().getNamaPerusahaan());
        updatedHeader.setRoleEntitas(event.getData().getRoleEntitas());

        // update some fields
        updatedHeader.setJumlahVolume(25_750.50);
        updatedHeader.setJumlahKontainer(100);
        updatedHeader.setJumlahNilaiBarang(BigDecimal.valueOf(10_000_000));

        var response = headerCommandService.updateDocumentHeader(updatedHeader, event.getData().getIdHeader()).log();

        StepVerifier.create(response)
                .consumeNextWith(header1 -> {
                    assertThat(header1.getIdHeader()).isEqualTo(event.getData().getIdHeader());
                    assertThat(header1.getNomorAju()).isEqualTo(event.getData().getNomorAju());
                    assertThat(header1.getAsalData()).isEqualTo(header.getAsalData());
                    assertThat(header1.getIdPerusahaan()).isEqualTo(header.getIdPerusahaan());
                    assertThat(header1.getRoleEntitas()).isEqualTo(header.getRoleEntitas());
                    assertThat(header1.getJumlahVolume()).isEqualTo(updatedHeader.getJumlahVolume());
                    assertThat(header1.getJumlahKontainer()).isEqualTo(updatedHeader.getJumlahKontainer());
                    assertThat(header1.getJumlahNilaiBarang()).isEqualTo(updatedHeader.getJumlahNilaiBarang());
                })
                .verifyComplete();
    }
}