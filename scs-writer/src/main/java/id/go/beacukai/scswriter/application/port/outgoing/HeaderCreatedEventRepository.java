package id.go.beacukai.scswriter.application.port.outgoing;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface HeaderCreatedEventRepository extends ReactiveCrudRepository<HeaderCreatedEvent, String> {
}
