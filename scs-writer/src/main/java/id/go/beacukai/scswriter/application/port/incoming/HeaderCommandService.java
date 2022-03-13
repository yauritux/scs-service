package id.go.beacukai.scswriter.application.port.incoming;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import id.go.beacukai.scs.sharedkernel.domain.event.HeaderUpdatedEvent;
import id.go.beacukai.scswriter.domain.entity.Header;
import reactor.core.publisher.Mono;

public interface HeaderCommandService {
    Mono<HeaderCreatedEvent> createDocumentHeader(Header header);
    Mono<HeaderUpdatedEvent> updateDocumentHeader(Header header, String id);
}
