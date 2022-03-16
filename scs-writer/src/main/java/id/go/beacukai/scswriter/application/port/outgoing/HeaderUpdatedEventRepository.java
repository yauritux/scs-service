package id.go.beacukai.scswriter.application.port.outgoing;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderUpdatedEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface HeaderUpdatedEventRepository extends ReactiveCrudRepository<HeaderUpdatedEvent, String> {
}
