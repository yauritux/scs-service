package id.go.beacukai.scswriter.application.port.outgoing;

import id.go.beacukai.scswriter.domain.entity.Header;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface HeaderCommandRepository extends ReactiveCrudRepository<Header, String> {
    Mono<Long> countByIdPerusahaan(String idPerusahaan);
}
