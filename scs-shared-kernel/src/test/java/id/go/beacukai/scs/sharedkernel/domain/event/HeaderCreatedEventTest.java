package id.go.beacukai.scs.sharedkernel.domain.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HeaderCreatedEventTest {

    private HeaderCreatedEvent headerCreatedEvent;
    private HeaderCreatedEvent.Payload eventPayload;
    private final String eventId = UUID.randomUUID().toString();
    private final String idHeader = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        headerCreatedEvent = new HeaderCreatedEvent(eventId);
        eventPayload = new HeaderCreatedEvent.Payload(idHeader, "20", "TESTING");
        headerCreatedEvent.setData(eventPayload);
        eventPayload.setAsalData("W");
        eventPayload.setIdPengguna("yauritux");
        eventPayload.setFreight(225.55);
        eventPayload.setIdPerusahaan("1234567890");
        eventPayload.setNamaPerusahaan("PT. DEMO PORTAL");
        eventPayload.setKodeIncoterm("ABC");
        eventPayload.setNamaIncoterm("TESTING_INCOTERM");
        eventPayload.setKodeCaraBayar("1");
        eventPayload.setNamaCaraBayar("CASH");
        eventPayload.setNamaProses("TESTING_PROSES");
        eventPayload.setVolume(1000.00);
    }

    @Test
    void testCreateHeaderCreatedEvent() {
        assertThat(headerCreatedEvent.getData()).isNotNull();
        assertThat(headerCreatedEvent.getData()).isEqualTo(eventPayload);
    }

    @Test
    void testHeaderCreatedEventToString() {
        var payload = new StringBuilder();
        payload.append("idHeader=" + headerCreatedEvent.getData().getIdHeader());
        payload.append(", asalData=" + headerCreatedEvent.getData().getAsalData());
        payload.append(", idPerusahaan=" + headerCreatedEvent.getData().getIdPerusahaan());
        payload.append(", kodeDokumen=" + headerCreatedEvent.getData().getKodeDokumen());
        payload.append(", kodeIncoterm=" + headerCreatedEvent.getData().getKodeIncoterm());
        payload.append(", namaCaraBayar=" + headerCreatedEvent.getData().getNamaCaraBayar());
        payload.append(", namaIncoterm=" + headerCreatedEvent.getData().getNamaIncoterm());
        payload.append(", namaPerusahaan=" + headerCreatedEvent.getData().getNamaPerusahaan());
        payload.append(", namaProses=" + headerCreatedEvent.getData().getNamaProses());
        payload.append(", nomorAju=" + headerCreatedEvent.getData().getNomorAju());
        assertThat(headerCreatedEvent.toString()).isEqualTo(
                String.format(
                        "Event[%s.HeaderCreatedEvent, eventId = %s, eventHandler = %s, timestamp = %s], " +
                                "Payload[HeaderCreatedEvent.Payload(%s)]",
                        headerCreatedEvent.getClass().getPackageName(),
                        eventId,
                        "HeaderCreatedEventHandler",
                        headerCreatedEvent.getTimestamp(),
                        payload.toString()
                )
        );
    }
}