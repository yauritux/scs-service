package id.go.beacukai.scswriter.domain.service;

import id.go.beacukai.scswriter.application.port.outgoing.HeaderBaseEventRepository;
import id.go.beacukai.scswriter.application.port.outgoing.HeaderCommandRepository;
import id.go.beacukai.scswriter.domain.entity.Header;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeaderCommandServiceImplTest {

    @Mock
    private HeaderCommandRepository headerCommandRepositoryMock;

    @Mock
    private HeaderBaseEventRepository headerBaseEventRepositoryMock;

    @Mock
    private TransactionalOperator operatorMock;

    private HeaderCommandServiceImpl headerCommandService;

    @BeforeEach
    void setUp() {
        headerCommandService = new HeaderCommandServiceImpl(
                headerCommandRepositoryMock, headerBaseEventRepositoryMock,
                operatorMock);
    }

    @AfterEach
    void tearDown() {
        headerCommandService = null;
    }

    @Test
    void createDocumentHeader_recordIsCreated() {
        Header header = spy(new Header());
        header.setIdHeader(UUID.randomUUID().toString());
        header.setAsalData("W");
        header.setJumlahKontainer(0);
        header.setIdPerusahaan("1234567890");
        header.setKodeDokumen("20");

        var createdEvent = header.toCreatedEvent();

        when(headerCommandRepositoryMock.countByIdPerusahaan(header.getIdPerusahaan()))
                .thenReturn(Mono.just(1L));
        when(headerCommandRepositoryMock.save(
                isA(Header.class))).thenReturn(Mono.just(header));
        when(operatorMock.transactional(any(Mono.class))).thenReturn(Mono.just(createdEvent));

        var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        var response = headerCommandService.createDocumentHeader(header).log();

        verify(header, atLeastOnce()).toCreatedEvent();

        StepVerifier.create(response)
                .consumeNextWith(event -> {
                    assert event != null;
                    assert event.getData().getIdHeader() != null;
                    assertEquals(event.getData().getIdHeader(), header.getIdHeader());
                    assertEquals(event.getData().getIdHeader(), header.toCreatedEvent().getData().getIdHeader());
                    assertTrue(event.getData().getIdHeader() instanceof String);
                    assert header.toCreatedEvent().getData().getNomorAju() != null;
                    assertEquals("000020123456" + currentDate + "000002", header.toCreatedEvent().getData().getNomorAju());
                })
                .verifyComplete();
    }

    @Test
    void createDocumentHeader_noGivenKodeDokumen_shouldGetAnError() {
        Header header = new Header();
        header.setIdHeader(UUID.randomUUID().toString());

        headerCommandService.createDocumentHeader(header).as(StepVerifier::create)
                .expectErrorMessage("\"kodeDokumen\" cannot be empty!")
                .verify();
    }

    @Test
    void createDocumentHeader_noGivenKodeDokumen_shouldGetADataIntegrityException() {
        Header header = new Header();
        header.setIdHeader(UUID.randomUUID().toString());

        headerCommandService.createDocumentHeader(header).as(StepVerifier::create)
                .expectError(DataIntegrityViolationException.class)
                .verify();
    }

    @Test
    void createDocumentHeader_noGivenIdPerusahaan_shouldGetAnError() {
        Header header = new Header();
        header.setIdHeader(UUID.randomUUID().toString());
        header.setKodeDokumen("20");

        headerCommandService.createDocumentHeader(header).as(StepVerifier::create)
                .expectErrorMessage("\"idPerusahaan\" cannot be empty!")
                .verify();
    }

    @Test
    void createDocumentHeader_noGivenIdPerusahaan_shouldGetADataIntegrityException() {
        Header header = new Header();
        header.setIdHeader(UUID.randomUUID().toString());
        header.setKodeDokumen("20");

        headerCommandService.createDocumentHeader(header).as(StepVerifier::create)
                .expectError(DataIntegrityViolationException.class)
                .verify();
    }

    @Test
    void updateDocumentHeader_existingHeaderId_recordIsUpdated() {
        var idHeader = UUID.randomUUID().toString();

        Header currentHeader = spy(new Header());
        currentHeader.setIdHeader(idHeader);
        currentHeader.setAsalData("W");
        currentHeader.setIdPerusahaan("1234567890");
        currentHeader.setNamaPerusahaan("PT. DEMO PORTAL");
        currentHeader.setRoleEntitas("IMPORTIR");
        currentHeader.setNomorAju("00002012345620220224000001");
        currentHeader.setKodeDokumen("20");

        Header updatedHeader = currentHeader;
        updatedHeader.setIsNew(false);
        updatedHeader.setJumlahVolume(2500.25);
        updatedHeader.setJumlahNilaiBarang(BigDecimal.valueOf(1_000_000));

        var updatedEvent = updatedHeader.toUpdatedEvent();

        when(headerCommandRepositoryMock.findById(idHeader)).thenReturn(Mono.just(currentHeader));
        when(headerCommandRepositoryMock.save(isA(Header.class))).thenReturn(Mono.just(updatedHeader));
        when(headerBaseEventRepositoryMock.countByAggregateId(any(String.class))).thenReturn(Mono.just(1L));
        when(operatorMock.transactional(any(Mono.class))).thenReturn(Mono.just(updatedEvent));

        verify(currentHeader, times(1)).setJumlahVolume(2500.25);
        verify(currentHeader, times(1)).setJumlahNilaiBarang(BigDecimal.valueOf(1_000_000));

        headerCommandService.updateDocumentHeader(updatedHeader, idHeader).as(StepVerifier::create)
                .consumeNextWith(newUpdatedHeader -> {
                    assert newUpdatedHeader != null;
                    assert newUpdatedHeader.getData().getIdHeader().equals(idHeader);
                    assert newUpdatedHeader.getData().getJumlahVolume() == 2500.25;
                    assert newUpdatedHeader.getData().getJumlahNilaiBarang().compareTo(BigDecimal.valueOf(1_000_000)) == 0;
                })
                .verifyComplete();
    }

    @Test
    void updateDocumentHeader_headerIdNotFound_noRecordIsUpdated() {
        var idHeader = UUID.randomUUID().toString();

        Header currentHeader = spy(new Header());
        currentHeader.setIdHeader(idHeader);
        currentHeader.setAsalData("W");
        currentHeader.setIdPerusahaan("1234567890");
        currentHeader.setNamaPerusahaan("PT. DEMO PORTAL");
        currentHeader.setRoleEntitas("IMPORTIR");
        currentHeader.setNomorAju("00002012345620220224000001");

        Header updatedHeader = currentHeader;
        updatedHeader.setJumlahVolume(2500.25);
        updatedHeader.setJumlahNilaiBarang(BigDecimal.valueOf(1_000_000));

        when(headerCommandRepositoryMock.findById(idHeader)).thenReturn(Mono.empty());

        var updateResponse = headerCommandService.updateDocumentHeader(updatedHeader, idHeader).log();

        verify(headerCommandRepositoryMock, never()).save(isA(Header.class));

        StepVerifier.create(updateResponse)
                .expectNextCount(0L)
                .verifyComplete();
    }
}