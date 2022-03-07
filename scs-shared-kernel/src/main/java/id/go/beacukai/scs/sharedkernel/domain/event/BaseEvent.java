package id.go.beacukai.scs.sharedkernel.domain.event;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
public class BaseEvent {
    @Id
    protected String eventId;
    protected String eventType;
    protected String eventHandler;
    protected String eventReferenceId;
    protected LocalDateTime timestamp;
    protected String createdBy;

    public BaseEvent(final String id) {
        this.eventId = id;
        this.eventType = toCamelCase(this.getClass().getName());
        this.eventHandler = this.getClass().getSimpleName() + "Handler";
        this.timestamp = LocalDateTime.now();
    }

    public BaseEvent(final String id, final String refId) {
        this(id);
        this.eventReferenceId = refId;
        this.createdBy = "system";
    }

    public BaseEvent(final String id, final boolean isSimpleEventName) {
        this.eventId = id;
        this.eventType = toCamelCase(isSimpleEventName ? this.getClass().getSimpleName() : this.getClass().getName());
        this.eventHandler = isSimpleEventName ?
                this.getClass().getSimpleName() + "Handler" : this.getClass().getName() + "Handler";
        this.timestamp = LocalDateTime.now();
    }

    public BaseEvent(final String id, final boolean isSimpleEventName, final String refId) {
        this(id, isSimpleEventName);
        this.eventReferenceId = refId;
        this.createdBy = "system";
    }

    public BaseEvent(final String id, final String createdBy, boolean isSimpleEventName) {
        this(id, isSimpleEventName);
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return String.format("%s, eventId = %s, eventHandler = %s, timestamp = %s",
                this.getEventType(), this.getEventId(), this.getEventHandler(), this.getTimestamp());
    }

    private String toCamelCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
