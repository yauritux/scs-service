package id.go.beacukai.scswriter.infrastructure.adapter.web.dto;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HeaderCreatedEventResponseModel {
    private String eventId;
    private String eventType;
    private long version;
    private String eventHandler;
    private String eventReferenceId;
    private HeaderPayloadResponseModel data;
    private LocalDateTime timestamp;
    private String createdBy;

    public HeaderCreatedEventResponseModel(HeaderCreatedEvent event) {
        this.eventId = event.getEventId();
        this.eventType = event.getEventType();
        this.version = event.getVersion();
        this.eventHandler = event.getEventHandler();
        this.eventReferenceId = event.getEventReferenceId();
        this.data = new HeaderPayloadResponseModel(event.getData());
        this.timestamp = event.getTimestamp();
        this.createdBy = event.getCreatedBy();
    }
}
