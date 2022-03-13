package id.go.beacukai.scswriter.application.port.outgoing;

import id.go.beacukai.scswriter.config.TestContainerConfiguration;
import id.go.beacukai.scswriter.domain.entity.Header;
import org.junit.jupiter.api.*;
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
import java.util.UUID;

@DataR2dbcTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles({"test"})
@Import(TestContainerConfiguration.class)
@Disabled
public class HeaderUpdatedEventRepositoryIT {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14");

    @Autowired
    private HeaderUpdatedEventRepository headerUpdatedEventRepository;

    private Header header;

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
        header = new Header();
        header.setIdHeader(UUID.randomUUID().toString());
        header.setKodeDokumen("20");
        header.setAsalData("W");
        header.setIdPerusahaan("1234567890");
        header.setNamaPerusahaan("DEMO PORTAL");
        header.setRoleEntitas("IMPORTIR");
        header.setNomorAju("00002012345620220222000001");
        var updatedEvent = header.toUpdatedEvent();
        updatedEvent.setAggregateId(header.getIdHeader());
        updatedEvent.setVersion(1l);
        headerUpdatedEventRepository.saveAll(List.of(updatedEvent)).blockLast();
    }

    @AfterEach
    void tearDown() {
        headerUpdatedEventRepository.deleteAll().block();
    }

    @Test
    @Disabled
    void findFirstByAggregateIdOrderByTimestampDesc() {
        var response = headerUpdatedEventRepository
                .findFirstByAggregateIdOrderByTimestampDesc(header.getIdHeader()).log();

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
    }
}
