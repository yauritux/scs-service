package id.go.beacukai.scswriter.domain.service;

import id.go.beacukai.scswriter.application.port.outgoing.HeaderCommandRepository;
import id.go.beacukai.scswriter.domain.entity.Header;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeaderCommandServiceImplTest {

    @Mock
    private HeaderCommandRepository headerCommandRepositoryMock;

    @InjectMocks
    private HeaderCommandServiceImpl headerCommandService;

    @Test
    void createDocumentHeader() {
        Header header = new Header();
        header.setAsalData("W");
        header.setJumlahKontainer(0);
        header.setIdPerusahaan("1234567890");
        header.setKodeDokumen("20");

        when(headerCommandRepositoryMock.countByIdPerusahaan(header.getIdPerusahaan()))
                .thenReturn(1L);
        when(headerCommandRepositoryMock.save(isA(Header.class))).thenReturn(Mono.just(header));

        var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        headerCommandService.createDocumentHeader(header).as(StepVerifier::create)
                .consumeNextWith(createdHeader -> {
                    assert createdHeader != null;
                    assert createdHeader.getIdHeader() != null;
                    assertTrue(createdHeader.getIdHeader() instanceof String);
                    assert createdHeader.getNomorAju() != null;
                    assertEquals("000020123456" + currentDate + "000002", createdHeader.getNomorAju());
                })
                .verifyComplete();
    }
}