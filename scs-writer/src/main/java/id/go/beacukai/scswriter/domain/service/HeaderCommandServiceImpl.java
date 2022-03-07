package id.go.beacukai.scswriter.domain.service;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import id.go.beacukai.scswriter.application.port.incoming.HeaderCommandService;
import id.go.beacukai.scswriter.application.port.outgoing.HeaderCommandRepository;
import id.go.beacukai.scswriter.application.port.outgoing.HeaderCreatedEventRepository;
import id.go.beacukai.scswriter.domain.entity.Header;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

@Service
public class HeaderCommandServiceImpl implements HeaderCommandService {

    private final HeaderCommandRepository headerCommandRepository;
    private final HeaderCreatedEventRepository headerCreatedEventRepository;
    private final TransactionalOperator operator;

    public HeaderCommandServiceImpl(
            HeaderCommandRepository headerCommandRepository, HeaderCreatedEventRepository headerCreatedEventRepository,
            TransactionalOperator operator) {
        this.headerCommandRepository = headerCommandRepository;
        this.headerCreatedEventRepository = headerCreatedEventRepository;
        this.operator = operator;
    }

    @Override
    public Mono<HeaderCreatedEvent> createDocumentHeader(Header header) {
        try {
            header.setNomorAju(newNomorAju(header.getKodeDokumen(), header.getIdPerusahaan()));
            return headerCommandRepository.save(header).thenReturn(header.toEvent())
                    .flatMap(headerCreatedEventRepository::save)
                    .doOnError(System.out::println).as(operator::transactional);
        } catch (DataIntegrityViolationException | ExecutionException | InterruptedException e) {
            return Mono.error(e);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Header> updateDocumentHeader(Header updatedHeader, String id) {
        return headerCommandRepository.findById(id)
                .flatMap(currentHeader -> {
                    currentHeader.setAsuransi(updatedHeader.getAsuransi());
                    currentHeader.setBiayaPengurang(updatedHeader.getBiayaPengurang());
                    currentHeader.setBiayaTambahan(updatedHeader.getBiayaTambahan());
                    currentHeader.setBruto(updatedHeader.getBruto());
                    currentHeader.setCif(updatedHeader.getCif());
                    currentHeader.setDasarPengenaanPajak(updatedHeader.getDasarPengenaanPajak());
                    currentHeader.setDisclaimer(updatedHeader.getDisclaimer());
                    currentHeader.setEmail(updatedHeader.getEmail());
                    currentHeader.setFlagCurah(updatedHeader.isFlagCurah());
                    currentHeader.setFlagMigas(updatedHeader.isFlagMigas());
                    currentHeader.setFlagPph(updatedHeader.isFlagPph());
                    currentHeader.setFlagSda(updatedHeader.isFlagSda());
                    currentHeader.setFlagVd(updatedHeader.isFlagVd());
                    currentHeader.setFob(updatedHeader.getFob());
                    currentHeader.setFreight(updatedHeader.getFreight());
                    currentHeader.setHargaPenyerahan(updatedHeader.getHargaPenyerahan());
                    currentHeader.setHargaPerolehan(updatedHeader.getHargaPerolehan());
                    currentHeader.setIdPelmuatAkhir(updatedHeader.getIdPelmuatAkhir());
                    currentHeader.setIdPengguna(updatedHeader.getIdPengguna());
                    currentHeader.setJatuhTempoBilling(updatedHeader.getJatuhTempoBilling());
                    currentHeader.setIdPpjk(updatedHeader.getIdPpjk());
                    currentHeader.setJabatanTtd(updatedHeader.getJabatanTtd());
                    currentHeader.setJumlahBruto(updatedHeader.getJumlahBruto());
                    currentHeader.setJumlahCif(updatedHeader.getJumlahCif());
                    currentHeader.setJumlahFob(updatedHeader.getJumlahFob());
                    currentHeader.setJumlahHargaPenyerahan(updatedHeader.getJumlahHargaPenyerahan());
                    currentHeader.setJumlahKontainer(updatedHeader.getJumlahKontainer());
                    currentHeader.setJumlahNetto(updatedHeader.getJumlahNetto());
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
                    currentHeader.setLokasiAsal(updatedHeader.getLokasiAsal());
                    currentHeader.setLokasiTujuan(updatedHeader.getLokasiTujuan());
                    return headerCommandRepository.save(currentHeader);
                });
    }

    private final String newNomorAju(String kodeDokumen, String idPerusahaan)
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
