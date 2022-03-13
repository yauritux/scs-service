package id.go.beacukai.scswriter.application.port.outgoing;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderUpdatedEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface HeaderUpdatedEventRepository extends ReactiveCrudRepository<HeaderUpdatedEvent, String> {

    Mono<HeaderUpdatedEvent> findFirstByAggregateIdOrderByTimestampDesc(String aggregateId);
}
