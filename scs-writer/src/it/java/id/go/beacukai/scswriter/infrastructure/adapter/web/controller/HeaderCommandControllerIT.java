package id.go.beacukai.scswriter.infrastructure.adapter.web.controller;

import id.go.beacukai.scswriter.application.port.incoming.HeaderCommandService;
import id.go.beacukai.scswriter.config.TestContainerConfiguration;
import id.go.beacukai.scswriter.domain.entity.Header;
import id.go.beacukai.scswriter.infrastructure.adapter.web.dto.HeaderCreatedEventResponseModel;
import id.go.beacukai.scswriter.infrastructure.adapter.web.dto.HeaderUpdatedEventResponseModel;
import org.junit.jupiter.api.*;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test" })
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestContainerConfiguration.class)
@AutoConfigureWebTestClient
class HeaderCommandControllerIT {

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
    @Order(1)
    void testHeaderCommandServiceExisted() {
        assertThat(headerCommandService).isNotNull();
    }

    @Test
    @Order(2)
    void createNewHeader_recordIsCreated() {
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
                .expectBody(HeaderCreatedEventResponseModel.class)
                .consumeWith(headerEntityExchangeResult -> {
                    var responseBody = headerEntityExchangeResult.getResponseBody();
                    assertThat(responseBody).isNotNull();
                    assertThat(responseBody.getData()).isNotNull();
                    assertThat(responseBody.getData().getIdHeader()).isNotNull();
                    assertThat(responseBody.getData().getIdHeader()).isInstanceOf(String.class);
                    assertThat(responseBody.getData().getNomorAju()).isNotNull();
                    assertThat(responseBody.getData().getNomorAju()).isEqualTo("000020123456" + currentDate + "000001");
                });
    }

    @Test
    @Order(3)
    void createNewHeader_badRequest_shouldGetAnErrorMessage() {
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

    @Test
    @Order(4)
    void updateHeader_existingHeader_recordIsUpdated() {
        var header = new Header();
        header.setIdHeader(UUID.randomUUID().toString());
        header.setKodeDokumen("20");
        header.setIdPerusahaan("1234567890");
        header.setNamaPerusahaan("PT. DEMO PORTAL");
        header.setUserPortal("yauritux");
        header.setRoleEntitas("IMPORTIR");
        header.setAsalData("W");

        var event = headerCommandService.createDocumentHeader(header).block();

        var updatedHeader = header;
        updatedHeader.setIsNew(false);
        updatedHeader.setNomorAju(event.getData().getNomorAju());
        updatedHeader.setAsalData(event.getData().getAsalData());
        updatedHeader.setKodeDokumen(event.getData().getKodeDokumen());
        updatedHeader.setIdPerusahaan(event.getData().getIdPerusahaan());
        updatedHeader.setNamaPerusahaan(event.getData().getNamaPerusahaan());
        updatedHeader.setRoleEntitas(event.getData().getRoleEntitas());
        updatedHeader.setJumlahNilaiBarang(BigDecimal.valueOf(95_000_000));
        updatedHeader.setLokasiAsal("Singapore");
        updatedHeader.setLokasiTujuan("Jakarta");
        updatedHeader.setJumlahKontainer(100);

        webTestClient
                .put()
                .uri(HEADER_URI + "/{id}", updatedHeader.getIdHeader())
                .bodyValue(updatedHeader)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(HeaderUpdatedEventResponseModel.class)
                .consumeWith(headerEntityExchangeResult -> {
                    var responseBody = headerEntityExchangeResult.getResponseBody();
                    assertThat(responseBody).isNotNull();
                    assertThat(responseBody.getData()).isNotNull();
                    assertThat(responseBody.getData().getIdHeader()).isEqualTo(updatedHeader.getIdHeader());
                    assertThat(responseBody.getData().getJumlahNilaiBarang()).isEqualTo(updatedHeader.getJumlahNilaiBarang());
                    assertThat(responseBody.getData().getLokasiAsal()).isEqualTo(updatedHeader.getLokasiAsal());
                    assertThat(responseBody.getData().getLokasiTujuan()).isEqualTo(updatedHeader.getLokasiTujuan());
                    assertThat(responseBody.getData().getJumlahKontainer()).isEqualTo(updatedHeader.getJumlahKontainer());
                });
    }

    @Test
    @Order(5)
    void updateHeader_unexistingHeader_notFoundIsReturned() {
        var updatedHeader = new Header();
        updatedHeader.setIdHeader("ABC");
        updatedHeader.setKodeDokumen("20");
        updatedHeader.setIdPerusahaan("1234567890");
        updatedHeader.setNamaPerusahaan("PT. DEMO PORTAL");
        updatedHeader.setRoleEntitas("IMPORTIR");
        updatedHeader.setJumlahNilaiBarang(BigDecimal.valueOf(95_000_000));
        updatedHeader.setLokasiAsal("Singapore");
        updatedHeader.setLokasiTujuan("Jakarta");
        updatedHeader.setJumlahKontainer(100);

        webTestClient
                .put()
                .uri(HEADER_URI + "/{id}", updatedHeader.getIdHeader())
                .bodyValue(updatedHeader)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}