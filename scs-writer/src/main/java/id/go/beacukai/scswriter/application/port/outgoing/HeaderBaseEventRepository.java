package id.go.beacukai.scswriter.application.port.outgoing;

import id.go.beacukai.scs.sharedkernel.domain.event.BaseEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface HeaderBaseEventRepository extends ReactiveCrudRepository<BaseEvent, String> {

    Mono<Long> countByAggregateId(String aggregateId);
}
