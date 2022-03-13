package id.go.beacukai.scswriter.domain.service;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import id.go.beacukai.scs.sharedkernel.domain.event.HeaderUpdatedEvent;
import id.go.beacukai.scswriter.application.port.incoming.HeaderCommandService;
import id.go.beacukai.scswriter.application.port.outgoing.HeaderCommandRepository;
import id.go.beacukai.scswriter.application.port.outgoing.HeaderCreatedEventRepository;
import id.go.beacukai.scswriter.application.port.outgoing.HeaderUpdatedEventRepository;
import id.go.beacukai.scswriter.domain.entity.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class HeaderCommandServiceImpl implements HeaderCommandService {

    private final HeaderCommandRepository headerCommandRepository;
    private final HeaderCreatedEventRepository headerCreatedEventRepository;
    private final HeaderUpdatedEventRepository headerUpdatedEventRepository;
    private final TransactionalOperator operator;

    public HeaderCommandServiceImpl(
            HeaderCommandRepository headerCommandRepository, HeaderCreatedEventRepository headerCreatedEventRepository,
            HeaderUpdatedEventRepository headerUpdatedEventRepository, TransactionalOperator operator) {
        this.headerCommandRepository = headerCommandRepository;
        this.headerCreatedEventRepository = headerCreatedEventRepository;
        this.headerUpdatedEventRepository = headerUpdatedEventRepository;
        this.operator = operator;
    }

    @Override
    public Mono<HeaderCreatedEvent> createDocumentHeader(Header header) {
        try {
            if (header.isNew() && header.getIdHeader() == null) {
                header.setIdHeader(UUID.randomUUID().toString());
            }
            header.setNomorAju(newNomorAju(header.getKodeDokumen(), header.getIdPerusahaan()));
            var createdEvent = header.toCreatedEvent();
            createdEvent.setVersion(0);
            createdEvent.setAggregateId(header.getIdHeader());
            return headerCommandRepository.save(header).thenReturn(createdEvent)
                    .flatMap(headerCreatedEventRepository::save)
                    .doOnError(System.out::println).as(operator::transactional);
        } catch (DataIntegrityViolationException | ExecutionException | InterruptedException e) {
            return Mono.error(e);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public Mono<HeaderUpdatedEvent> updateDocumentHeader(Header updatedHeader, String id) {
        return headerCommandRepository.findById(id)
                .flatMap(currentHeader -> {
                    currentHeader.setIsNew(false); // MUST flag as false to direct the R2DBC process for update operation
                    if (updatedHeader.getAsuransi() != null) {
                        currentHeader.setAsuransi(updatedHeader.getAsuransi());
                    }
                    if (updatedHeader.getBiayaPengurang() != null) {
                        currentHeader.setBiayaPengurang(updatedHeader.getBiayaPengurang());
                    }
                    if (updatedHeader.getBiayaTambahan() != null) {
                        currentHeader.setBiayaTambahan(updatedHeader.getBiayaTambahan());
                    }
                    if (updatedHeader.getBruto() != null) {
                        currentHeader.setBruto(updatedHeader.getBruto());
                    }
                    if (updatedHeader.getCif() != null) {
                        currentHeader.setCif(updatedHeader.getCif());
                    }
                    if (updatedHeader.getDasarPengenaanPajak() != null) {
                        currentHeader.setDasarPengenaanPajak(updatedHeader.getDasarPengenaanPajak());
                    }
                    if (updatedHeader.getDisclaimer() != null) {
                        currentHeader.setDisclaimer(updatedHeader.getDisclaimer());
                    }
                    if (updatedHeader.getEmail() != null) {
                        currentHeader.setEmail(updatedHeader.getEmail());
                    }
                    if (updatedHeader.isFlagCurah() != currentHeader.isFlagCurah()) {
                        currentHeader.setFlagCurah(updatedHeader.isFlagCurah());
                    }
                    if (updatedHeader.isFlagMigas() != currentHeader.isFlagMigas()) {
                        currentHeader.setFlagMigas(updatedHeader.isFlagMigas());
                    }
                    if (updatedHeader.isFlagPph() != currentHeader.isFlagPph()) {
                        currentHeader.setFlagPph(updatedHeader.isFlagPph());
                    }
                    if (updatedHeader.isFlagSda() != currentHeader.isFlagSda()) {
                        currentHeader.setFlagSda(updatedHeader.isFlagSda());
                    }
                    if (updatedHeader.isFlagVd() != currentHeader.isFlagVd()) {
                        currentHeader.setFlagVd(updatedHeader.isFlagVd());
                    }
                    if (updatedHeader.getFob() != null) {
                        currentHeader.setFob(updatedHeader.getFob());
                    }
                    if (updatedHeader.getFreight() != null) {
                        currentHeader.setFreight(updatedHeader.getFreight());
                    }
                    if (updatedHeader.getHargaPenyerahan() != null) {
                        currentHeader.setHargaPenyerahan(updatedHeader.getHargaPenyerahan());
                    }
                    if (updatedHeader.getHargaPerolehan() != null) {
                        currentHeader.setHargaPerolehan(updatedHeader.getHargaPerolehan());
                    }
                    if (updatedHeader.getIdPelmuatAkhir() != null) {
                        currentHeader.setIdPelmuatAkhir(updatedHeader.getIdPelmuatAkhir());
                    }
                    if (updatedHeader.getIdPengguna() != null && !updatedHeader.getIdPengguna().equalsIgnoreCase(currentHeader.getIdPengguna())) {
                        currentHeader.setIdPengguna(updatedHeader.getIdPengguna());
                    }
                    if (updatedHeader.getJatuhTempoBilling() != null) {
                        currentHeader.setJatuhTempoBilling(updatedHeader.getJatuhTempoBilling());
                    }
                    if (updatedHeader.getIdPpjk() != null) {
                        currentHeader.setIdPpjk(updatedHeader.getIdPpjk());
                    }
                    if (updatedHeader.getJabatanTtd() != null) {
                        currentHeader.setJabatanTtd(updatedHeader.getJabatanTtd());
                    }
                    if (updatedHeader.getJumlahBruto() != null) {
                        currentHeader.setJumlahBruto(updatedHeader.getJumlahBruto());
                    }
                    if (updatedHeader.getJumlahCif() != null) {
                        currentHeader.setJumlahCif(updatedHeader.getJumlahCif());
                    }
                    if (updatedHeader.getJumlahFob() != null) {
                        currentHeader.setJumlahFob(updatedHeader.getJumlahFob());
                    }
                    if (updatedHeader.getJumlahHargaPenyerahan() != null) {
                        currentHeader.setJumlahHargaPenyerahan(updatedHeader.getJumlahHargaPenyerahan());
                    }
                    if (updatedHeader.getJumlahKontainer() != null) {
                        currentHeader.setJumlahKontainer(updatedHeader.getJumlahKontainer());
                    }
                    if (updatedHeader.getJumlahNetto() != null) {
                        currentHeader.setJumlahNetto(updatedHeader.getJumlahNetto());
                    }
                    currentHeader.setJumlahNilaiBarang(updatedHeader.getJumlahNilaiBarang());
                    currentHeader.setJumlahNilaiVd(updatedHeader.getJumlahNilaiVd());
                    currentHeader.setJumlahTandaPengaman(updatedHeader.getJumlahTandaPengaman());
                    currentHeader.setJumlahVolume(updatedHeader.getJumlahVolume());
                    currentHeader.setKodeAsalBarangFtz(updatedHeader.getKodeAsalBarangFtz());
                    currentHeader.setKodeAsuransi(updatedHeader.getKodeAsuransi());
                    currentHeader.setKodeBank(updatedHeader.getKodeBank());
                    currentHeader.setKodeBilling(updatedHeader.getKodeBilling());
                    currentHeader.setKodeCaraAngkutPlb(updatedHeader.getKodeCaraAngkutPlb());
                    currentHeader.setKodeCaraDagang(updatedHeader.getKodeCaraDagang());
                    currentHeader.setKodeCaraBayar(updatedHeader.getKodeCaraBayar());
                    currentHeader.setKodeDaerahAsal(updatedHeader.getKodeDaerahAsal());
                    currentHeader.setKodeFaktur(updatedHeader.getKodeFaktur());
                    currentHeader.setKodeGudangAsal(updatedHeader.getKodeGudangAsal());
                    currentHeader.setKodeGudangTujuan(updatedHeader.getKodeGudangTujuan());
                    currentHeader.setKodeIncoterm(updatedHeader.getKodeIncoterm());
                    currentHeader.setKodeJenisEkspor(updatedHeader.getKodeJenisEkspor());
                    currentHeader.setKodeJenisImpor(updatedHeader.getKodeJenisImpor());
                    currentHeader.setKodeJenisKirim(updatedHeader.getKodeJenisKirim());
                    currentHeader.setKodeJenisNilai(updatedHeader.getKodeJenisNilai());
                    currentHeader.setKodeJenisPengiriman(updatedHeader.getKodeJenisPengiriman());
                    currentHeader.setKodeJenisPlb(updatedHeader.getKodeJenisPlb());
                    currentHeader.setKodeJenisProsedur(updatedHeader.getKodeJenisProsedur());
                    currentHeader.setKodeJenisTandaPengaman(updatedHeader.getKodeJenisTandaPengaman());
                    currentHeader.setKodeJenisTpb(updatedHeader.getKodeJenisTpb());
                    currentHeader.setKodeKantor(updatedHeader.getKodeKantor());
                    currentHeader.setKodeKantorBongkar(updatedHeader.getKodeKantorBongkar());
                    currentHeader.setKodeKantorEkspor(updatedHeader.getKodeKantorEkspor());
                    currentHeader.setKodeKantorMuat(updatedHeader.getKodeKantorMuat());
                    currentHeader.setKodeKantorPeriksa(updatedHeader.getKodeKantorPeriksa());
                    currentHeader.setKodeKantorTujuan(updatedHeader.getKodeKantorTujuan());
                    if (updatedHeader.getLokasiAsal() != null) {
                        currentHeader.setLokasiAsal(updatedHeader.getLokasiAsal());
                    }
                    if (updatedHeader.getLokasiTujuan() != null) {
                        currentHeader.setLokasiTujuan(updatedHeader.getLokasiTujuan());
                    }
                    var updatedEvent = currentHeader.toUpdatedEvent();
                    updatedEvent.setAggregateId(currentHeader.getIdHeader());
//                    var currentAggregateRecord = headerUpdatedEventRepository
//                            .findFirstByAggregateIdOrderByTimestampDesc(updatedHeader.getIdHeader()).toFuture();
//                    try {
//                        var lastAggregateRecord = currentAggregateRecord.get();
//                        if (lastAggregateRecord != null) {
//                            updatedEvent.setVersion(lastAggregateRecord.getVersion() + 1);
//                        }
//                    } catch (InterruptedException | ExecutionException e) {
//                        log.error(e.getMessage());
//                    }
                    // TODO set updatedEvent version
                    return headerCommandRepository.save(currentHeader).thenReturn(updatedEvent)
                            .flatMap(headerUpdatedEventRepository::save)
                            .doOnError(System.out::println)
                            .as(operator::transactional);
                });
    }

    private String newNomorAju(String kodeDokumen, String idPerusahaan)
            throws DataIntegrityViolationException, ExecutionException, InterruptedException {
        if (kodeDokumen == null || kodeDokumen.trim().equals("")) {
            throw new DataIntegrityViolationException("\"kodeDokumen\" cannot be empty!");
        }
        if (idPerusahaan == null || idPerusahaan.trim().equals("")) {
            throw new DataIntegrityViolationException("\"idPerusahaan\" cannot be empty!");
        }
        var str = new StringBuffer();
        str.append("0".repeat(6 - kodeDokumen.length()));
        str.append(kodeDokumen);
        str.append(idPerusahaan.substring(0, 6));
        var df = DateTimeFormatter.ofPattern("yyyyMMdd");
        str.append(LocalDate.now().format(df));
        synchronized (this) {
            var count = headerCommandRepository.countByIdPerusahaan(idPerusahaan).toFuture().get();
            var nextNo = count + 1;
            str.append("0".repeat(6 - Long.toString(nextNo).length()));
            str.append(nextNo);
        }
        return str.toString();
    }
}
