package id.go.beacukai.scs.sharedkernel.domain.event;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BaseEventTest {

    @Test
    void createBaseEventWithGivenId() {
        var id = UUID.randomUUID().toString();
        var event = new BaseEvent(id);
        assertThat(event).isNotNull();
        assertThat(event.getEventId()).isEqualTo(id);
        assertThat(event.getEventType()).isEqualTo(event.getClass().getName());
        assertThat(event.getEventHandler()).isEqualTo("BaseEventHandler");
        assertThat(event.getTimestamp()).isNotNull();
        assertThat(event.getTimestamp()).isInstanceOf(LocalDateTime.class);
    }

    @Test
    void createBaseEventWithGivenIdAndRefId() {
        var id = UUID.randomUUID().toString();
        var refId = UUID.randomUUID().toString();
        var event = new BaseEvent(id, refId);
        assertThat(event).isNotNull();
        assertThat(event.getEventId()).isEqualTo(id);
        assertThat(event.getEventReferenceId()).isEqualTo(refId);
        assertThat(event.getCreatedBy()).isEqualTo("system");
        assertThat(event.getEventType()).isEqualTo(event.getClass().getName());
        assertThat(event.getEventHandler()).isEqualTo("BaseEventHandler");
        assertThat(event.getTimestamp()).isInstanceOf(LocalDateTime.class);
    }

    @Test
    void createBaseEventWithGivenIdAndSimpleEventNameAndRefId() {
        var id = UUID.randomUUID().toString();
        var refId = UUID.randomUUID().toString();
        var event = new BaseEvent(id, true, refId);
        assertThat(event).isNotNull();
        assertThat(event.getEventId()).isEqualTo(id);
        assertThat(event.getEventReferenceId()).isEqualTo(refId);
        assertThat(event.getCreatedBy()).isEqualTo("system");
        assertThat(event.getEventHandler()).isEqualTo("BaseEventHandler");
        assertThat(event.getEventType()).isEqualTo("baseEvent");
        assertThat(event.toString()).isEqualTo(String.format("%s, eventId = %s, eventHandler = %s, timestamp = %s",
                event.getEventType(),
                event.getEventId(),
                event.getEventHandler(),
                event.getTimestamp()));
    }

    @Test
    void createBaseEventWithGivenIdAndRefIdWithNoSimpleEventName() {
        var id = UUID.randomUUID().toString();
        var refId = UUID.randomUUID().toString();
        var event = new BaseEvent(id, false, refId);
        assertThat(event).isNotNull();
        assertThat(event.getEventId()).isEqualTo(id);
        assertThat(event.getEventReferenceId()).isEqualTo(refId);
        assertThat(event.getCreatedBy()).isEqualTo("system");
        assertThat(event.getEventHandler()).isEqualTo(event.getClass().getPackageName() + ".BaseEventHandler");
        assertThat(event.getEventType()).isEqualTo(event.getClass().getPackageName() + ".BaseEvent");
        assertThat(event.toString()).isEqualTo(String.format("%s, eventId = %s, eventHandler = %s, timestamp = %s",
                event.getEventType(),
                event.getEventId(),
                event.getEventHandler(),
                event.getTimestamp()));
    }

    @Test
    void createBaseEventWithGivenIdAndCreatedByAndSimpleEventName() {
        var id = UUID.randomUUID().toString();
        var refId = UUID.randomUUID().toString();
        var event = new BaseEvent(id, "yauritux", true);
        assertThat(event).isNotNull();
        assertThat(event.getEventId()).isEqualTo(id);
        assertThat(event.getEventReferenceId()).isNull();
        assertThat(event.getCreatedBy()).isEqualTo("yauritux");
        assertThat(event.getEventHandler()).isEqualTo("BaseEventHandler");
        assertThat(event.getEventType()).isEqualTo("baseEvent");
        assertThat(event.toString()).isEqualTo(String.format("%s, eventId = %s, eventHandler = %s, timestamp = %s",
                event.getEventType(),
                event.getEventId(),
                event.getEventHandler(),
                event.getTimestamp()));
    }

    @Test
    void createBaseEventWithGivenIdAndCreatedByWithNoSimpleEventName() {
        var id = UUID.randomUUID().toString();
        var refId = UUID.randomUUID().toString();
        var event = new BaseEvent(id, "yauritux", false);
        assertThat(event).isNotNull();
        assertThat(event.getEventId()).isEqualTo(id);
        assertThat(event.getEventReferenceId()).isNull();
        assertThat(event.getCreatedBy()).isEqualTo("yauritux");
        assertThat(event.getEventHandler()).isEqualTo(event.getClass().getPackageName() + ".BaseEventHandler");
        assertThat(event.getEventType()).isEqualTo(event.getClass().getPackageName() + ".BaseEvent");
        assertThat(event.toString()).isEqualTo(String.format("%s, eventId = %s, eventHandler = %s, timestamp = %s",
                event.getEventType(),
                event.getEventId(),
                event.getEventHandler(),
                event.getTimestamp()));
    }
}