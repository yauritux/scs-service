package id.go.beacukai.scswriter.domain.service;

import id.go.beacukai.scswriter.application.port.incoming.HeaderCommandService;
import id.go.beacukai.scswriter.domain.entity.Header;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles({ "test" })
class HeaderCommandServiceImplIntTest {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14");

    @Autowired
    HeaderCommandService headerCommandService;

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://" +
                postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort() +
                "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
            ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
            initializer.setConnectionFactory(connectionFactory);

            CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
            populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
            initializer.setDatabasePopulator(populator);

            return initializer;
        }
    }

    @Test
    void testHeaderComandServiceExisted() {
        assertThat(headerCommandService).isNotNull();
    }

    @Test
    void createDocumentHeader() {
        var header = new Header();
        header.setKodeDokumen("20");
        header.setAsalData("W");
        header.setIdPerusahaan("1234567890");
        header.setNamaPerusahaan("DEMO PORTAL");
        header.setRoleEntitas("IMPORTIR");

        var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        var createdHeader = headerCommandService.createDocumentHeader(header).log();

        StepVerifier.create(createdHeader)
                .consumeNextWith(newHeader -> {
                    assertThat(newHeader).isNotNull();
                    assertThat(newHeader.getIdHeader()).isNotNull();
                    assertThat(newHeader.getIdHeader()).isInstanceOf(String.class);
                    assertThat(newHeader.getNomorAju()).isNotNull();
                    assertThat(newHeader.getNomorAju()).isEqualTo("000020123456" + currentDate + "000001");
                })
                .verifyComplete();
    }
}