package id.go.beacukai.scswriter.domain.service;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import id.go.beacukai.scs.sharedkernel.domain.event.HeaderUpdatedEvent;
import id.go.beacukai.scswriter.application.port.incoming.HeaderCommandService;
import id.go.beacukai.scswriter.application.port.outgoing.HeaderBaseEventRepository;
import id.go.beacukai.scswriter.application.port.outgoing.HeaderCommandRepository;
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
    private final HeaderBaseEventRepository headerBaseEventRepository;
    private final TransactionalOperator operator;

    public HeaderCommandServiceImpl(
            HeaderCommandRepository headerCommandRepository, HeaderBaseEventRepository headerBaseEventRepository,
            TransactionalOperator operator) {
        this.headerCommandRepository = headerCommandRepository;
        this.headerBaseEventRepository = headerBaseEventRepository;
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
                    .flatMap(headerBaseEventRepository::save)
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
                    if (updatedHeader.getJumlahNilaiBarang() != null) {
                        currentHeader.setJumlahNilaiBarang(updatedHeader.getJumlahNilaiBarang());
                    }
                    if (updatedHeader.getJumlahNilaiVd() != null) {
                        currentHeader.setJumlahNilaiVd(updatedHeader.getJumlahNilaiVd());
                    }
                    if (updatedHeader.getJumlahTandaPengaman() != null) {
                        currentHeader.setJumlahTandaPengaman(updatedHeader.getJumlahTandaPengaman());
                    }
                    if (updatedHeader.getJumlahVolume() != null) {
                        currentHeader.setJumlahVolume(updatedHeader.getJumlahVolume());
                    }
                    if (updatedHeader.getKodeAsalBarangFtz() != null) {
                        currentHeader.setKodeAsalBarangFtz(updatedHeader.getKodeAsalBarangFtz());
                    }
                    if (updatedHeader.getKodeAsuransi() != null) {
                        currentHeader.setKodeAsuransi(updatedHeader.getKodeAsuransi());
                    }
                    if (updatedHeader.getKodeBank() != null) {
                        currentHeader.setKodeBank(updatedHeader.getKodeBank());
                    }
                    if (updatedHeader.getKodeBilling() != null) {
                        currentHeader.setKodeBilling(updatedHeader.getKodeBilling());
                    }
                    if (updatedHeader.getKodeCaraAngkutPlb() != null) {
                        currentHeader.setKodeCaraAngkutPlb(updatedHeader.getKodeCaraAngkutPlb());
                    }
                    if (updatedHeader.getKodeCaraDagang() != null) {
                        currentHeader.setKodeCaraDagang(updatedHeader.getKodeCaraDagang());
                    }
                    if (updatedHeader.getKodeCaraBayar() != null) {
                        currentHeader.setKodeCaraBayar(updatedHeader.getKodeCaraBayar());
                    }
                    if (updatedHeader.getKodeDaerahAsal() != null) {
                        currentHeader.setKodeDaerahAsal(updatedHeader.getKodeDaerahAsal());
                    }
                    if (updatedHeader.getKodeFaktur() != null) {
                        currentHeader.setKodeFaktur(updatedHeader.getKodeFaktur());
                    }
                    if (updatedHeader.getKodeGudangAsal() != null) {
                        currentHeader.setKodeGudangAsal(updatedHeader.getKodeGudangAsal());
                    }
                    if (updatedHeader.getKodeGudangTujuan() != null) {
                        currentHeader.setKodeGudangTujuan(updatedHeader.getKodeGudangTujuan());
                    }
                    if (updatedHeader.getKodeIncoterm() != null) {
                        currentHeader.setKodeIncoterm(updatedHeader.getKodeIncoterm());
                    }
                    if (updatedHeader.getKodeJenisEkspor() != null) {
                        currentHeader.setKodeJenisEkspor(updatedHeader.getKodeJenisEkspor());
                    }
                    if (updatedHeader.getKodeJenisImpor() != null) {
                        currentHeader.setKodeJenisImpor(updatedHeader.getKodeJenisImpor());
                    }
                    if (updatedHeader.getKodeJenisKirim() != null) {
                        currentHeader.setKodeJenisKirim(updatedHeader.getKodeJenisKirim());
                    }
                    if (updatedHeader.getKodeJenisNilai() != null) {
                        currentHeader.setKodeJenisNilai(updatedHeader.getKodeJenisNilai());
                    }
                    if (updatedHeader.getKodeJenisPengiriman() != null) {
                        currentHeader.setKodeJenisPengiriman(updatedHeader.getKodeJenisPengiriman());
                    }
                    if (updatedHeader.getKodeJenisPlb() != null) {
                        currentHeader.setKodeJenisPlb(updatedHeader.getKodeJenisPlb());
                    }
                    if (updatedHeader.getKodeJenisProsedur() != null) {
                        currentHeader.setKodeJenisProsedur(updatedHeader.getKodeJenisProsedur());
                    }
                    if (updatedHeader.getKodeJenisTandaPengaman() != null) {
                        currentHeader.setKodeJenisTandaPengaman(updatedHeader.getKodeJenisTandaPengaman());
                    }
                    if (updatedHeader.getKodeJenisTpb() != null) {
                        currentHeader.setKodeJenisTpb(updatedHeader.getKodeJenisTpb());
                    }
                    if (updatedHeader.getKodeKantor() != null) {
                        currentHeader.setKodeKantor(updatedHeader.getKodeKantor());
                    }
                    if (updatedHeader.getKodeKantorBongkar() != null) {
                        currentHeader.setKodeKantorBongkar(updatedHeader.getKodeKantorBongkar());
                    }
                    if (updatedHeader.getKodeKantorEkspor() != null) {
                        currentHeader.setKodeKantorEkspor(updatedHeader.getKodeKantorEkspor());
                    }
                    if (updatedHeader.getKodeKantorMuat() != null) {
                        currentHeader.setKodeKantorMuat(updatedHeader.getKodeKantorMuat());
                    }
                    if (updatedHeader.getKodeKantorPeriksa() != null) {
                        currentHeader.setKodeKantorPeriksa(updatedHeader.getKodeKantorPeriksa());
                    }
                    if (updatedHeader.getKodeKantorTujuan() != null) {
                        currentHeader.setKodeKantorTujuan(updatedHeader.getKodeKantorTujuan());
                    }
                    if (updatedHeader.getLokasiAsal() != null) {
                        currentHeader.setLokasiAsal(updatedHeader.getLokasiAsal());
                    }
                    if (updatedHeader.getLokasiTujuan() != null) {
                        currentHeader.setLokasiTujuan(updatedHeader.getLokasiTujuan());
                    }
                    final HeaderUpdatedEvent updatedEvent = currentHeader.toUpdatedEvent();
                    updatedEvent.setAggregateId(currentHeader.getIdHeader());
                    var totalAggregateRecords = getTotalAggregateRecords(currentHeader.getIdHeader()).log();

                    return totalAggregateRecords.flatMap(c -> {
                        updatedEvent.setVersion((c + 1) - 1); // minus 1 as event's version always started from '0'
                        return headerCommandRepository.save(currentHeader).thenReturn(updatedEvent)
                                .flatMap(headerBaseEventRepository::save)
                                .doOnError(System.out::println)
                                .as(operator::transactional);
                    });
                });
    }

    private synchronized Mono<Long> getTotalAggregateRecords(String aggregateId) {
        return headerBaseEventRepository.countByAggregateId(aggregateId);
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
