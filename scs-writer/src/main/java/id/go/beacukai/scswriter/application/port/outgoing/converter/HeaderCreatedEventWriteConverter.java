package id.go.beacukai.scswriter.application.port.outgoing.converter;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class HeaderCreatedEventWriteConverter implements Converter<HeaderCreatedEvent, OutboundRow> {

    @Override
    public OutboundRow convert(HeaderCreatedEvent event) {
        OutboundRow row = new OutboundRow();
        row.put("event_id", Parameter.from(event.getEventId()));
        row.put("event_type", Parameter.from(event.getEventType()));
        row.put("event_handler", Parameter.from(event.getEventHandler()));
        row.put("version", Parameter.from(event.getVersion()));
        if (event.getEventReferenceId() != null) {
            row.put("event_reference_id", Parameter.from(event.getEventReferenceId()));
        }
        row.put("data", Parameter.from(event.getData().toString()));
        row.put("timestamp", Parameter.from(event.getTimestamp()));
        if (event.getCreatedBy() != null) {
            row.put("created_by", Parameter.from(event.getCreatedBy()));
        }
        return row;
    }
}
