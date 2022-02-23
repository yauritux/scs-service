package id.go.beacukai.scswriter.infrastructure.adapter.web.controller;

import id.go.beacukai.scswriter.application.port.incoming.HeaderCommandService;
import id.go.beacukai.scswriter.config.TestContainerConfiguration;
import id.go.beacukai.scswriter.domain.entity.Header;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test" })
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Testcontainers
@Import(TestContainerConfiguration.class)
@AutoConfigureWebTestClient
class HeaderCommandControllerIntTest {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14");

    @Autowired
    private HeaderCommandService headerCommandService;

    @Autowired
    private WebTestClient webTestClient;

    static String HEADER_URI = "/v2/headers";

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://" +
                postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort() +
                "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
    }

    @Test
    void testHeaderCommandServiceExisted() {
        assertThat(headerCommandService).isNotNull();
    }

    @Test
    void createNewHeader() {
        var header = new Header();
        header.setKodeDokumen("20");
        header.setAsalData("W");
        header.setIdPerusahaan("1234567890");
        header.setNamaPerusahaan("DEMO PORTAL");
        header.setRoleEntitas("IMPORTIR");

        var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        webTestClient
                .post()
                .uri(HEADER_URI)
                .bodyValue(header)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Header.class)
                .consumeWith(headerEntityExchangeResult -> {
                    var responseBody = headerEntityExchangeResult.getResponseBody();
                    assertThat(responseBody).isNotNull();
                    assertThat(responseBody.getIdHeader()).isNotNull();
                    assertThat(responseBody.getIdHeader()).isInstanceOf(String.class);
                    assertThat(responseBody.getNomorAju()).isNotNull();
                    assertThat(responseBody.getNomorAju()).isEqualTo("000020123456" + currentDate + "000001");
                });
    }

    @Test
    void createNewHeader_badRequest() {
        var header = new Header();
        header.setKodeDokumen("20");
        header.setRoleEntitas("IMPORTIR");

        webTestClient
                .post()
                .uri(HEADER_URI)
                .bodyValue(header)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    var responseBody = stringEntityExchangeResult.getResponseBody();
                    var expectedErrorMessage = "asalData tidak boleh kosong!,idPerusahaan tidak boleh kosong!,namaPerusahaan tidak boleh kosong!";
                    assertThat(responseBody).isEqualTo(expectedErrorMessage);
                });
    }
}