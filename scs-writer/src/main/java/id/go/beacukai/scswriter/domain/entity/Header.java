package id.go.beacukai.scswriter.domain.entity;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import id.go.beacukai.scs.sharedkernel.domain.event.HeaderUpdatedEvent;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(value = "header")
@Data
public class Header implements Persistable<String> {

    @Transient
    private boolean isNew = false;

    @Id
    private String idHeader;
    @NotBlank(message = "asalData tidak boleh kosong!")
    private String asalData;
    private BigDecimal asuransi;
    private BigDecimal biayaPengurang;
    private BigDecimal biayaTambahan;
    private BigDecimal bruto;
    private String cif;
    private String dasarPengenaanPajak;
    private String disclaimer;
    private String email;
    private boolean flagCurah;
    private boolean flagMigas;
    private boolean flagPph;
    private boolean flagSda;
    private boolean flagVd;
    private String fob;
    private Double freight;
    private BigDecimal hargaPenyerahan;
    private BigDecimal hargaPerolehan;
    private String idPelmuatAkhir;
    private String idPengguna;
    @NotBlank(message = "idPerusahaan tidak boleh kosong!")
    private String idPerusahaan;
    private String idPpjk;
    private String jabatanTtd;
    private LocalDate jatuhTempoBilling;
    private BigDecimal jumlahBruto;
    private BigDecimal jumlahCif;
    private BigDecimal jumlahFob;
    private BigDecimal jumlahHargaPenyerahan;
    private Integer jumlahKontainer;
    private Integer jumlahNetto;
    private BigDecimal jumlahNilaiBarang;
    private BigDecimal jumlahNilaiVd;
    private Integer jumlahTandaPengaman;
    private Double jumlahVolume;
    private String kodeAsalBarangFtz;
    private String kodeAsuransi;
    private String kodeBank;
    private String kodeBilling;
    private String kodeCaraAngkutPlb;
    private String kodeCaraBayar;
    private String kodeCaraDagang;
    private String kodeDaerahAsal;
    @NotBlank(message = "kodeDokumen tidak boleh kosong!")
    private String kodeDokumen;
    private String kodeFaktur;
    private String kodeGudangAsal;
    private String kodeGudangTujuan;
    private String kodeIncoterm;
    private String kodeJenisEkspor;
    private String kodeJenisImpor;
    private String kodeJenisKirim;
    private String kodeJenisNilai;
    private String kodeJenisPengiriman;
    private String kodeJenisPlb;
    private String kodeJenisProsedur;
    private String kodeJenisTandaPengaman;
    private String kodeJenisTpb;
    private String kodeKantor;
    private String kodeKantorBongkar;
    private String kodeKantorEkspor;
    private String kodeKantorMuat;
    private String kodeKantorPeriksa;
    private String kodeKantorTujuan;
    private String kodeKategoriBarangFtz;
    private String kodeKategoriEkspor;
    private String kodeKategoriKeluarFtz;
    private String kodeKategoriMasukFtz;
    private String kodeKenaPajak;
    private String kodeLokasi;
    private String kodeLokasiBayar;
    private String kodeNegaraTujuan;
    private String kodePelBongkar;
    private String kodePelEkspor;
    private String kodePelMuat;
    private String kodePelTransit;
    private String kodePelTujuan;
    private String kodePembayar;
    private String kodeTps;
    private String kodeTujuanPemasukan;
    private String kodeTujuanPengiriman;
    private String kodeTujuanTpb;
    private String kodeTutupPu;
    private String kodeValuta;
    private String kodeTtd;
    private String lokasiAsal;
    private String lokasiTujuan;
    private String namaCaraBayar;
    private String namaIncoterm;
    private String namaJenisImpor;
    private String namaJenisNilai;
    private String namaJenisProsedur;
    private String namaJenisTpb;
    private String namaKantorBongkar;
    private String namaKantorMuat;
    private String namaKantorPendek;
    private String namaKantorTujuan;
    private String namaPelabuhanBongkar;
    private String namaPelabuhanMuat;
    private String namaPelabuhanTujuan;
    @NotBlank(message = "namaPerusahaan tidak boleh kosong!")
    private String namaPerusahaan;
    private String namaPpjk;
    private String namaProses;
    private String namaTransaksiLainnyaFtz;
    private String namaTtd;
    private String namaValuta;
    private String ndpbm;
    private String netto;
    private String nik;
    private BigDecimal nilaiBarang;
    private BigDecimal nilaiIncoterm;
    private BigDecimal nilaiJasa;
    private BigDecimal nilaiKurs;
    private BigDecimal nilaiMaklon;
    private String nipRekam;
    @Column("nomor_aju")
    private String nomorAju;
    private String nomorBc11;
    private String nomorBuktiBayar;
    private String nomorDaftar;
    private String npppjk;
    private String npwpBilling;
    private String npwpPemusatan;
    private String posBc11;
    @NotBlank(message = "roleEntitas tidak boleh kosong!")
    private String roleEntitas;
    private int seri;
    private String subposBc11;
    private LocalDate tanggalAju;
    private LocalDate tanggalBc11;
    private LocalDate tanggalBerangkat;
    private LocalDate tanggalBilling;
    private LocalDate tanggalBuktiBayar;
    private LocalDate tanggalDaftar;
    private LocalDate tanggalEkspor;
    private LocalDate tanggalIjinPenerima;
    private LocalDate tanggalIjinPengusaha;
    private LocalDate tanggalMasuk;
    private LocalDate tanggalMuat;
    private LocalDate tanggalNpppjk;
    private LocalDate tanggalPeriksa;
    private LocalDate tanggalStuffing;
    private LocalDate tglAkhirBerlaku;
    private LocalDate tglAwalBerlaku;
    private BigDecimal totalDanaSawit;
    private String ttBarang;
    private BigDecimal uangMuka;
    private String urlDokumen;
    private String userPortal;
    private String vd;
    private String versiModul;
    private Double volume;

    public Header() {
        this.isNew = true;
    }

    // TODO:: find a way to abstracting toCreatedEvent and toUpdatedEvent
    public HeaderCreatedEvent toCreatedEvent() {
        var headerCreatedEvent = new HeaderCreatedEvent(UUID.randomUUID().toString());
        var eventPayload = new HeaderCreatedEvent.Payload(
                this.idHeader, this.kodeDokumen, this.nomorAju);
        eventPayload.setIdPerusahaan(this.idPerusahaan);
        eventPayload.setNamaPerusahaan(this.namaPerusahaan);
        eventPayload.setRoleEntitas(this.roleEntitas);
        eventPayload.setAsalData(this.asalData);
        eventPayload.setJumlahKontainer(this.jumlahKontainer);
        eventPayload.setVolume(this.volume);
        eventPayload.setJumlahVolume(this.jumlahVolume);
        eventPayload.setKodeCaraBayar(this.kodeCaraBayar);
        eventPayload.setNamaCaraBayar(this.namaCaraBayar);
        eventPayload.setFreight(this.freight);
        eventPayload.setKodeIncoterm(this.kodeIncoterm);
        eventPayload.setNamaIncoterm(this.namaIncoterm);
        eventPayload.setIdPengguna(this.idPengguna);
        eventPayload.setAsuransi(this.asuransi);
        eventPayload.setKodeAsuransi(this.kodeAsuransi);
        eventPayload.setBiayaPengurang(this.biayaPengurang);
        eventPayload.setBiayaTambahan(this.biayaTambahan);
        eventPayload.setNetto(this.netto);
        eventPayload.setJumlahNetto(this.jumlahNetto);
        eventPayload.setBruto(this.bruto);
        eventPayload.setJumlahBruto(this.jumlahBruto);
        eventPayload.setCif(this.cif);
        eventPayload.setJumlahCif(this.jumlahCif);
        eventPayload.setDasarPengenaanPajak(this.dasarPengenaanPajak);
        eventPayload.setEmail(this.email);
        eventPayload.setDisclaimer(this.disclaimer);
        eventPayload.setFlagCurah(this.flagCurah);
        eventPayload.setFlagMigas(this.flagMigas);
        eventPayload.setFlagPph(this.flagPph);
        eventPayload.setFlagSda(this.flagSda);
        eventPayload.setFlagVd(this.flagVd);
        eventPayload.setJumlahNilaiVd(this.jumlahNilaiVd);
        eventPayload.setFob(this.fob);
        eventPayload.setJumlahFob(this.jumlahFob);
        eventPayload.setHargaPenyerahan(this.hargaPenyerahan);
        eventPayload.setJumlahHargaPenyerahan(this.jumlahHargaPenyerahan);
        eventPayload.setHargaPerolehan(this.hargaPerolehan);
        eventPayload.setNamaProses(this.namaProses);
        eventPayload.setIdPpjk(this.idPpjk);
        eventPayload.setJabatanTtd(this.jabatanTtd);
        eventPayload.setIdPelmuatAkhir(this.idPelmuatAkhir);
        eventPayload.setJumlahNilaiBarang(this.jumlahNilaiBarang);
        eventPayload.setJatuhTempoBilling(this.jatuhTempoBilling);
        eventPayload.setKodeAsalBarangFtz(this.kodeAsalBarangFtz);
        eventPayload.setLokasiAsal(this.lokasiAsal);
        eventPayload.setLokasiTujuan(this.lokasiTujuan);
        eventPayload.setUserPortal(this.userPortal);
        headerCreatedEvent.setData(eventPayload);
        return headerCreatedEvent;
    }

    public HeaderUpdatedEvent toUpdatedEvent() {
        var headerUpdatedEvent = new HeaderUpdatedEvent(UUID.randomUUID().toString());
        var eventPayload = new HeaderUpdatedEvent.Payload(
                this.idHeader, this.kodeDokumen, this.nomorAju);
        eventPayload.setIdPerusahaan(this.idPerusahaan);
        eventPayload.setNamaPerusahaan(this.namaPerusahaan);
        eventPayload.setRoleEntitas(this.roleEntitas);
        eventPayload.setAsalData(this.asalData);
        eventPayload.setJumlahKontainer(this.jumlahKontainer);
        eventPayload.setVolume(this.volume);
        eventPayload.setJumlahVolume(this.jumlahVolume);
        eventPayload.setKodeCaraBayar(this.kodeCaraBayar);
        eventPayload.setNamaCaraBayar(this.namaCaraBayar);
        eventPayload.setFreight(this.freight);
        eventPayload.setKodeIncoterm(this.kodeIncoterm);
        eventPayload.setNamaIncoterm(this.namaIncoterm);
        eventPayload.setIdPengguna(this.idPengguna);
        eventPayload.setAsuransi(this.asuransi);
        eventPayload.setKodeAsuransi(this.kodeAsuransi);
        eventPayload.setBiayaPengurang(this.biayaPengurang);
        eventPayload.setBiayaTambahan(this.biayaTambahan);
        eventPayload.setNetto(this.netto);
        eventPayload.setJumlahNetto(this.jumlahNetto);
        eventPayload.setBruto(this.bruto);
        eventPayload.setJumlahBruto(this.jumlahBruto);
        eventPayload.setCif(this.cif);
        eventPayload.setJumlahCif(this.jumlahCif);
        eventPayload.setDasarPengenaanPajak(this.dasarPengenaanPajak);
        eventPayload.setEmail(this.email);
        eventPayload.setDisclaimer(this.disclaimer);
        eventPayload.setFlagCurah(this.flagCurah);
        eventPayload.setFlagMigas(this.flagMigas);
        eventPayload.setFlagPph(this.flagPph);
        eventPayload.setFlagSda(this.flagSda);
        eventPayload.setFlagVd(this.flagVd);
        eventPayload.setJumlahNilaiVd(this.jumlahNilaiVd);
        eventPayload.setFob(this.fob);
        eventPayload.setJumlahFob(this.jumlahFob);
        eventPayload.setHargaPenyerahan(this.hargaPenyerahan);
        eventPayload.setJumlahHargaPenyerahan(this.jumlahHargaPenyerahan);
        eventPayload.setHargaPerolehan(this.hargaPerolehan);
        eventPayload.setNamaProses(this.namaProses);
        eventPayload.setIdPpjk(this.idPpjk);
        eventPayload.setJabatanTtd(this.jabatanTtd);
        eventPayload.setIdPelmuatAkhir(this.idPelmuatAkhir);
        eventPayload.setJumlahNilaiBarang(this.jumlahNilaiBarang);
        eventPayload.setJatuhTempoBilling(this.jatuhTempoBilling);
        eventPayload.setKodeAsalBarangFtz(this.kodeAsalBarangFtz);
        eventPayload.setLokasiAsal(this.lokasiAsal);
        eventPayload.setLokasiTujuan(this.lokasiTujuan);
        eventPayload.setUserPortal(this.userPortal);
        headerUpdatedEvent.setData(eventPayload);
        return headerUpdatedEvent;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public String getId() {
        return idHeader;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
