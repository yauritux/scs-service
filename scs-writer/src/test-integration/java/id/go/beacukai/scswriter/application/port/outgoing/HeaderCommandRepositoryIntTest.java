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
}