package id.go.beacukai.scswriter.application.port.outgoing;

import id.go.beacukai.scswriter.domain.entity.Header;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface HeaderCommandRepository extends ReactiveCrudRepository<Header, String> {
    long countByIdPerusahaan(String idPerusahaan);
}
