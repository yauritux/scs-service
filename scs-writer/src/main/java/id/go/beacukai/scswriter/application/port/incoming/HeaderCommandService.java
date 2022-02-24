package id.go.beacukai.scswriter.application.port.incoming;

import id.go.beacukai.scswriter.domain.entity.Header;
import reactor.core.publisher.Mono;

public interface HeaderCommandService {
    Mono<Header> createDocumentHeader(Header header);
    Mono<Header> updateDocumentHeader(Header header, String id);
}
