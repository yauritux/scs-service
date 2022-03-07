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
    private String eventHandler;
    private String eventReferenceId;
    private HeaderPayloadResponseModel data;
    private LocalDateTime timestamp;
    private String createdBy;

    public HeaderCreatedEventResponseModel(HeaderCreatedEvent event) {
        var response = new HeaderCreatedEventResponseModel();
        response.setEventId(event.getEventId());
        response.setEventType(event.getEventType());
        response.setEventHandler(event.getEventHandler());
        response.setEventReferenceId(event.getEventReferenceId());
        response.setData(new HeaderPayloadResponseModel(event.getData()));
        response.setTimestamp(event.getTimestamp());
        response.setCreatedBy(event.getCreatedBy());
    }
}
