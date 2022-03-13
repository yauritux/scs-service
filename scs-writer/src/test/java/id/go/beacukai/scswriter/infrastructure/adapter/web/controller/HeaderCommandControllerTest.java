package id.go.beacukai.scswriter.infrastructure.adapter.web.controller;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import id.go.beacukai.scswriter.domain.entity.Header;
import id.go.beacukai.scswriter.domain.service.HeaderCommandServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeaderCommandControllerTest {

    @Mock
    private HeaderCommandServiceImpl headerCommandServiceMock;

    @InjectMocks
    HeaderCommandController headerCommandController;

    @Test
    void createNewHeader() {

        var nomorAju = "00002012345620220220789326";

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
        newDocumentHeader.setNomorAju(nomorAju);

        var headerCreatedEvent = new HeaderCreatedEvent(UUID.randomUUID().toString());
//        var eventPayload = headerCreatedEvent.new Payload(newDocumentHeader.getIdHeader(),
//                newDocumentHeader.getKodeDokumen(), newDocumentHeader.getNomorAju());
        var eventPayload = new HeaderCreatedEvent.Payload(newDocumentHeader.getIdHeader(),
                newDocumentHeader.getKodeDokumen(), newDocumentHeader.getNomorAju());
        eventPayload.setIdPerusahaan(newDocumentHeader.getIdPerusahaan());
        eventPayload.setNamaPerusahaan(newDocumentHeader.getNamaPerusahaan());
        eventPayload.setAsalData(newDocumentHeader.getAsalData());
        eventPayload.setJumlahKontainer(newDocumentHeader.getJumlahKontainer());
        eventPayload.setRoleEntitas(newDocumentHeader.getRoleEntitas());
        eventPayload.setSeri(newDocumentHeader.getSeri());
        eventPayload.setUserPortal(newDocumentHeader.getUserPortal());
        headerCreatedEvent.setData(eventPayload);

        when(headerCommandServiceMock.createDocumentHeader(isA(Header.class))).thenReturn(Mono.just(headerCreatedEvent));

        var response = headerCommandController.createNewHeader(newDocumentHeader);

        StepVerifier.create(response)
                .consumeNextWith(event -> {
                    assertNotNull(event);
                    assertEquals(nomorAju, event.getData().getNomorAju());
                    assertEquals("W", event.getData().getAsalData());
                    assertEquals("IMPORTIR", event.getData().getRoleEntitas());
                })
                .verifyComplete();
    }
}