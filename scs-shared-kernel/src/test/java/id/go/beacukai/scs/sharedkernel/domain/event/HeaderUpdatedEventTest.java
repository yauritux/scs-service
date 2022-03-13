package id.go.beacukai.scs.sharedkernel.domain.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HeaderUpdatedEventTest {

    private HeaderUpdatedEvent headerUpdatedEvent;
    private HeaderUpdatedEvent.Payload eventPayload;
    private final String eventId = UUID.randomUUID().toString();
    private final String idHeader = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        headerUpdatedEvent = new HeaderUpdatedEvent(eventId);
        eventPayload = new HeaderUpdatedEvent.Payload(idHeader, "20", "TESTING");
        headerUpdatedEvent.setData(eventPayload);
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
    void testCreateHeaderUpdatedEvent() {
        assertThat(headerUpdatedEvent.getData()).isNotNull();
        assertThat(headerUpdatedEvent.getData()).isEqualTo(eventPayload);
    }

    @Test
    void testHeaderUpdatedEventToString() {
        var payload = new StringBuilder();
        payload.append("idHeader=" + headerUpdatedEvent.getData().getIdHeader());
        payload.append(", asalData=" + headerUpdatedEvent.getData().getAsalData());
        payload.append(", idPerusahaan=" + headerUpdatedEvent.getData().getIdPerusahaan());
        payload.append(", kodeDokumen=" + headerUpdatedEvent.getData().getKodeDokumen());
        payload.append(", kodeIncoterm=" + headerUpdatedEvent.getData().getKodeIncoterm());
        payload.append(", namaCaraBayar=" + headerUpdatedEvent.getData().getNamaCaraBayar());
        payload.append(", namaIncoterm=" + headerUpdatedEvent.getData().getNamaIncoterm());
        payload.append(", namaPerusahaan=" + headerUpdatedEvent.getData().getNamaPerusahaan());
        payload.append(", namaProses=" + headerUpdatedEvent.getData().getNamaProses());
        payload.append(", nomorAju=" + headerUpdatedEvent.getData().getNomorAju());
        assertThat(headerUpdatedEvent.toString()).isEqualTo(
                String.format(
                        "Event[%s.HeaderUpdatedEvent, eventId = %s, eventHandler = %s, timestamp = %s], " +
                                "Payload[HeaderUpdatedEvent.Payload(%s)]",
                        headerUpdatedEvent.getClass().getPackageName(),
                        eventId,
                        "HeaderUpdatedEventHandler",
                        headerUpdatedEvent.getTimestamp(),
                        payload
                )
        );
    }
}
