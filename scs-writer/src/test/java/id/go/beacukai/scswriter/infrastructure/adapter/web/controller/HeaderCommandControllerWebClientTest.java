package id.go.beacukai.scswriter.infrastructure.adapter.web.controller;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import id.go.beacukai.scswriter.application.port.incoming.HeaderCommandService;
import id.go.beacukai.scswriter.domain.entity.Header;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = HeaderCommandController.class)
@AutoConfigureWebTestClient
public class HeaderCommandControllerWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private HeaderCommandService headerCommandServiceMock;

    static String HEADER_URL = "/v2/headers";

    @Test
    void createNewHeader_headerIsCreated() {
        var newDocumentHeader = new Header();
        newDocumentHeader.setIdHeader(UUID.randomUUID().toString());
        newDocumentHeader.setAsalData("W");
        newDocumentHeader.setIdPerusahaan("123456789012345");
        newDocumentHeader.setJumlahKontainer(0);
        newDocumentHeader.setKodeDokumen("20");
        newDocumentHeader.setNamaPerusahaan("PT DEMO PORTAL");
        newDocumentHeader.setRoleEntitas("IMPORTIR");
        newDocumentHeader.setSeri(0);
        newDocumentHeader.setUserPortal("yauritux");
        newDocumentHeader.setNamaPpjk("");
        newDocumentHeader.setNomorAju("00002012345620220220789326");

        when(headerCommandServiceMock.createDocumentHeader(any(Header.class)))
                .thenReturn(Mono.just(newDocumentHeader.toEvent()));

        webTestClient
                .post()
                .uri(HEADER_URL)
                .bodyValue(newDocumentHeader)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(HeaderCreatedEvent.class)
                .value(event -> {
                    System.out.println("event = " + event);
                    assertNotNull(event);
                });
//                .consumeWith(headerEntityExchangeResult -> {
//                    assertEquals(newDocumentHeader.getIdHeader(), responseBody.getData().getIdHeader());
//                    assertEquals(newDocumentHeader.getNomorAju(), responseBody.getData().getNomorAju());
//                });
    }

    @Test
    void createNewHeader_badRequest_shouldGetAnErrorMessage() {
        var newDocumentHeader = new Header();
        newDocumentHeader.setIdHeader(UUID.randomUUID().toString());

        webTestClient
                .post()
                .uri(HEADER_URL)
                .bodyValue(newDocumentHeader)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    var responseBody = stringEntityExchangeResult.getResponseBody();
                    var expectedErrorMessage = "asalData tidak boleh kosong!,idPerusahaan tidak boleh kosong!,kodeDokumen tidak boleh kosong!,namaPerusahaan tidak boleh kosong!,roleEntitas tidak boleh kosong!";
                    assertNotNull(responseBody);
                    assertEquals(expectedErrorMessage, responseBody);
                });
    }

    @Test
    void updateHeader_existingHeader_recordIsUpdated() {
        var idHeader = UUID.randomUUID().toString();

        var updatedHeader = new Header();
        updatedHeader.setIdHeader(idHeader);
        updatedHeader.setAsalData("W");
        updatedHeader.setIdPerusahaan("1234567890");
        updatedHeader.setNamaPerusahaan("PT. DEMO PORTAL");
        updatedHeader.setRoleEntitas("IMPORTIR");
        updatedHeader.setKodeDokumen("20");

        when(headerCommandServiceMock.updateDocumentHeader(updatedHeader, idHeader)).thenReturn(Mono.just(updatedHeader));

        webTestClient
                .put()
                .uri(HEADER_URL + "/{id}", idHeader)
                .bodyValue(updatedHeader)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Header.class)
                .consumeWith(headerEntityExchangeResult -> {
                    var responseBody = headerEntityExchangeResult.getResponseBody();
                    assertNotNull(responseBody);
                    assertEquals(updatedHeader.getIdHeader(), responseBody.getIdHeader());
                    assertEquals(updatedHeader.getKodeDokumen(), responseBody.getKodeDokumen());
                });
    }

    @Test
    void updateHeader_nonExistingHeader_gotNotFoundResponse() {
        var idHeader = UUID.randomUUID().toString();

        var updatedHeader = new Header();
        updatedHeader.setIdHeader(idHeader);
        updatedHeader.setAsalData("W");
        updatedHeader.setIdPerusahaan("1234567890");
        updatedHeader.setNamaPerusahaan("PT. DEMO PORTAL");
        updatedHeader.setRoleEntitas("IMPORTIR");
        updatedHeader.setKodeDokumen("20");

        when(headerCommandServiceMock.updateDocumentHeader(updatedHeader, idHeader)).thenReturn(Mono.empty());

        webTestClient
                .put()
                .uri(HEADER_URL + "/{id}", idHeader)
                .bodyValue(updatedHeader)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
