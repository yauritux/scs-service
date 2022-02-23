package id.go.beacukai.scswriter.domain.service;

import id.go.beacukai.scswriter.application.port.incoming.HeaderCommandService;
import id.go.beacukai.scswriter.application.port.outgoing.HeaderCommandRepository;
import id.go.beacukai.scswriter.domain.entity.Header;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class HeaderCommandServiceImpl implements HeaderCommandService {

    private final HeaderCommandRepository headerCommandRepository;

    public HeaderCommandServiceImpl(HeaderCommandRepository headerCommandRepository) {
        this.headerCommandRepository = headerCommandRepository;
    }

    @Override
    public Mono<Header> createDocumentHeader(Header header) {
        header.setIdHeader(UUID.randomUUID().toString());
        header.setNomorAju(newNomorAju(header.getKodeDokumen(), header.getIdPerusahaan()));
        return headerCommandRepository.save(header);
    }

    private final String newNomorAju(String kodeDokumen, String idPerusahaan) {
        var str = new StringBuffer();
        str.append("0".repeat(6 - kodeDokumen.length()));
        str.append(kodeDokumen);
        str.append(idPerusahaan.substring(0, 6));
        var df = DateTimeFormatter.ofPattern("yyyyMMdd");
        str.append(LocalDate.now().format(df));
        synchronized (this) {
            var nextNo = headerCommandRepository.countByIdPerusahaan(idPerusahaan).block() + 1;
            str.append("0".repeat(6 - Long.toString(nextNo).length()));
            str.append(nextNo);
        }
        return str.toString();
    }
}
