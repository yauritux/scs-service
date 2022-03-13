package id.go.beacukai.scswriter.infrastructure.adapter.web.dto;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderUpdatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HeaderUpdatedEventResponseModel {

    private String eventId;
    private String eventType;
    private String eventHandler;
    private String eventReferenceId;
    private HeaderPayloadResponseModel data;
    private LocalDateTime timestamp;
    private String createdBy;

    public HeaderUpdatedEventResponseModel(HeaderUpdatedEvent event) {
        this.eventId = event.getEventId();
        this.eventType = event.getEventType();
        this.eventHandler = event.getEventHandler();
        this.eventReferenceId = event.getEventReferenceId();
        this.data = new HeaderPayloadResponseModel(event.getData());
        this.timestamp = event.getTimestamp();
        this.createdBy = event.getCreatedBy();
    }
}
