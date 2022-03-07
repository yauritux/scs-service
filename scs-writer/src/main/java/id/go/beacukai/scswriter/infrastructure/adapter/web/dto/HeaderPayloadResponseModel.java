package id.go.beacukai.scswriter.infrastructure.adapter.web.dto;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class HeaderPayloadResponseModel {

    @ToString.Include
    @EqualsAndHashCode.Include
    private String idHeader;

    @ToString.Include
    @EqualsAndHashCode.Include
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

    @EqualsAndHashCode.Include
    @ToString.Include
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

    @EqualsAndHashCode.Include
    @ToString.Include
    private String kodeDokumen;

    private String kodeFaktur;
    private String kodeGudangAsal;
    private String kodeGudangTujuan;

    @ToString.Include
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

    @ToString.Include
    private String namaCaraBayar;

    @ToString.Include
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

    @ToString.Include
    private String namaPerusahaan;
    private String namaPpjk;

    @ToString.Include
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

    @EqualsAndHashCode.Include
    @ToString.Include
    private String nomorAju;

    private String nomorBc11;
    private String nomorBuktiBayar;
    private String nomorDaftar;
    private String npppjk;
    private String npwpBilling;
    private String npwpPemusatan;
    private String posBc11;
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

    public HeaderPayloadResponseModel(final HeaderCreatedEvent.Payload payload) {
        this.idHeader = payload.getIdHeader();
        this.asalData = payload.getAsalData();
        this.asuransi = payload.getAsuransi();
        this.biayaPengurang = payload.getBiayaPengurang();
        this.biayaTambahan = payload.getBiayaTambahan();
        this.bruto = payload.getBruto();
        this.cif = payload.getCif();
        this.dasarPengenaanPajak = payload.getDasarPengenaanPajak();
        this.disclaimer = payload.getDisclaimer();
        this.email = payload.getEmail();
        this.flagCurah = payload.isFlagCurah();
        this.flagMigas = payload.isFlagMigas();
        this.flagPph = payload.isFlagPph();
        this.flagSda = payload.isFlagSda();
        this.flagVd = payload.isFlagVd();
        this.fob = payload.getFob();
        this.freight = payload.getFreight();
        this.hargaPenyerahan = payload.getHargaPenyerahan();
        this.hargaPerolehan = payload.getHargaPerolehan();
        this.idPelmuatAkhir = payload.getIdPelmuatAkhir();
        this.idPengguna = payload.getIdPengguna();
        this.idPerusahaan = payload.getIdPerusahaan();
        this.idPpjk = payload.getIdPpjk();
        this.jabatanTtd = payload.getJabatanTtd();
        this.jatuhTempoBilling = payload.getJatuhTempoBilling();
        this.jumlahBruto = payload.getJumlahBruto();
        this.jumlahCif = payload.getJumlahCif();
        this.jumlahFob = payload.getJumlahFob();
        this.jumlahHargaPenyerahan = payload.getJumlahHargaPenyerahan();
        this.jumlahKontainer = payload.getJumlahKontainer();
        this.jumlahNetto = payload.getJumlahNetto();
        this.jumlahNilaiBarang = payload.getJumlahNilaiBarang();
        this.jumlahNilaiVd = payload.getJumlahNilaiVd();
        this.jumlahTandaPengaman = payload.getJumlahTandaPengaman();
        this.jumlahVolume = payload.getJumlahVolume();
        this.kodeAsalBarangFtz = payload.getKodeAsalBarangFtz();
        this.kodeAsuransi = payload.getKodeAsuransi();
        this.kodeBank = payload.getKodeBank();
        this.kodeBilling = payload.getKodeBilling();
        this.kodeCaraAngkutPlb = payload.getKodeCaraAngkutPlb();
        this.kodeCaraBayar = payload.getKodeCaraBayar();
        this.kodeCaraDagang = payload.getKodeCaraDagang();
        this.kodeDaerahAsal = payload.getKodeDaerahAsal();
        this.kodeDokumen = payload.getKodeDokumen();
        this.kodeFaktur = payload.getKodeFaktur();
        this.kodeGudangAsal = payload.getKodeGudangAsal();
        this.kodeGudangTujuan = payload.getKodeGudangTujuan();
        this.kodeIncoterm = payload.getKodeIncoterm();
        this.kodeJenisEkspor = payload.getKodeJenisEkspor();
        this.kodeJenisImpor = payload.getKodeJenisImpor();
        this.kodeJenisKirim = payload.getKodeJenisKirim();
        this.kodeJenisNilai = payload.getKodeJenisNilai();
        this.kodeJenisPengiriman = payload.getKodeJenisPengiriman();
        this.kodeJenisPlb = payload.getKodeJenisPlb();
        this.kodeJenisProsedur = payload.getKodeJenisProsedur();
        this.kodeJenisTandaPengaman = payload.getKodeJenisTandaPengaman();
        this.kodeJenisTpb = payload.getKodeJenisTpb();
        this.kodeKantor = payload.getKodeKantor();
        this.kodeKantorBongkar = payload.getKodeKantorBongkar();
        this.kodeKantorEkspor = payload.getKodeKantorEkspor();
        this.kodeKantorMuat = payload.getKodeKantorMuat();
        this.kodeKantorPeriksa = payload.getKodeKantorPeriksa();
        this.kodeKantorTujuan = payload.getKodeKantorTujuan();
        this.kodeKategoriBarangFtz = payload.getKodeKategoriBarangFtz();
        this.kodeKategoriEkspor = payload.getKodeKategoriEkspor();
        this.kodeKategoriKeluarFtz = payload.getKodeKategoriKeluarFtz();
        this.kodeKategoriMasukFtz = payload.getKodeKategoriMasukFtz();
        this.kodeKenaPajak = payload.getKodeKenaPajak();
        this.kodeLokasi = payload.getKodeLokasi();
        this.kodeLokasiBayar = payload.getKodeLokasiBayar();
        this.kodeNegaraTujuan = payload.getKodeNegaraTujuan();
        this.kodePelBongkar = payload.getKodePelBongkar();
        this.kodePelEkspor = payload.getKodePelEkspor();
        this.kodePelMuat = payload.getKodePelMuat();
        this.kodePelTransit = payload.getKodePelTransit();
        this.kodePelTujuan = payload.getKodePelTujuan();
        this.kodePembayar = payload.getKodePembayar();
        this.kodeTps = payload.getKodeTps();
        this.kodeTujuanPemasukan = payload.getKodeTujuanPemasukan();
        this.kodeTujuanPengiriman = payload.getKodeTujuanPengiriman();
        this.kodeTujuanTpb = payload.getKodeTujuanTpb();
        this.kodeTutupPu = payload.getKodeTutupPu();
        this.kodeValuta = payload.getKodeValuta();
        this.kodeTtd = payload.getKodeTtd();
        this.lokasiAsal = payload.getLokasiAsal();
        this.lokasiTujuan = payload.getLokasiTujuan();
        this.namaCaraBayar = payload.getNamaCaraBayar();
        this.namaIncoterm = payload.getNamaIncoterm();
        this.namaJenisImpor = payload.getNamaJenisImpor();
        this.namaJenisNilai = payload.getNamaJenisNilai();
        this.namaJenisProsedur = payload.getNamaJenisProsedur();
        this.namaJenisTpb = payload.getNamaJenisTpb();
        this.namaKantorBongkar = payload.getNamaKantorBongkar();
        this.namaKantorMuat = payload.getNamaKantorMuat();
        this.namaKantorPendek = payload.getNamaKantorPendek();
        this.namaKantorTujuan = payload.getNamaKantorTujuan();
        this.namaPelabuhanBongkar = payload.getNamaPelabuhanBongkar();
        this.namaPelabuhanMuat = payload.getNamaPelabuhanMuat();
        this.namaPelabuhanTujuan = payload.getNamaPelabuhanTujuan();
        this.namaPerusahaan = payload.getNamaPerusahaan();
        this.namaPpjk = payload.getNamaPpjk();
        this.namaProses = payload.getNamaProses();
        this.namaTransaksiLainnyaFtz = payload.getNamaTransaksiLainnyaFtz();
        this.namaTtd = payload.getNamaTtd();
        this.namaValuta = payload.getNamaValuta();
        this.ndpbm = payload.getNdpbm();
        this.netto = payload.getNetto();
        this.nik = payload.getNik();
        this.nilaiBarang = payload.getNilaiBarang();
        this.nilaiIncoterm = payload.getNilaiIncoterm();
        this.nilaiJasa = payload.getNilaiJasa();
        this.nilaiKurs = payload.getNilaiKurs();
        this.nilaiMaklon = payload.getNilaiMaklon();
        this.nipRekam = payload.getNipRekam();
        this.nomorAju = payload.getNomorAju();
        this.nomorBc11 = payload.getNomorBc11();
        this.nomorBuktiBayar = payload.getNomorBuktiBayar();
        this.nomorDaftar = payload.getNomorDaftar();
        this.npppjk = payload.getNpppjk();
        this.npwpBilling = payload.getNpwpBilling();
        this.npwpPemusatan = payload.getNpwpPemusatan();
        this.posBc11 = payload.getPosBc11();
        this.roleEntitas = payload.getRoleEntitas();
        this.seri = payload.getSeri();
        this.subposBc11 = payload.getSubposBc11();
        this.tanggalAju = payload.getTanggalAju();
        this.tanggalBc11 = payload.getTanggalBc11();
        this.tanggalBerangkat = payload.getTanggalBerangkat();
        this.tanggalBilling = payload.getTanggalBilling();
        this.tanggalBuktiBayar = payload.getTanggalBuktiBayar();
        this.tanggalDaftar = payload.getTanggalDaftar();
        this.tanggalEkspor = payload.getTanggalEkspor();
        this.tanggalIjinPenerima = payload.getTanggalIjinPenerima();
        this.tanggalIjinPengusaha = payload.getTanggalIjinPengusaha();
        this.tanggalMasuk = payload.getTanggalMasuk();
        this.tanggalMuat = payload.getTanggalMuat();
        this.tanggalNpppjk = payload.getTanggalNpppjk();
        this.tanggalPeriksa = payload.getTanggalPeriksa();
        this.tanggalStuffing = payload.getTanggalStuffing();
        this.tglAkhirBerlaku = payload.getTglAkhirBerlaku();
        this.tglAwalBerlaku = payload.getTglAwalBerlaku();
        this.totalDanaSawit = payload.getTotalDanaSawit();
        this.ttBarang = payload.getTtBarang();
        this.uangMuka = payload.getUangMuka();
        this.urlDokumen = payload.getUrlDokumen();
        this.userPortal = payload.getUserPortal();
        this.vd = payload.getVd();
        this.versiModul = payload.getVersiModul();
        this.volume = payload.getVolume();
    }
}
