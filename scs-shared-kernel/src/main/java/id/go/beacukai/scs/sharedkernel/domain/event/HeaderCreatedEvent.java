package id.go.beacukai.scs.sharedkernel.domain.event;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(value = "domain_events")
@Getter
@Setter
public class HeaderCreatedEvent extends BaseEvent {

    private Payload data;

    public HeaderCreatedEvent() {
        super();
    }

    public HeaderCreatedEvent(String id) {
        super(id);
    }

    @Override
    public String toString() {
        return String.format("Event[%s], Payload[%s]", super.toString(), this.data.toString());
    }

    @Getter
    @Setter
    @ToString(onlyExplicitlyIncluded = true)
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @NoArgsConstructor
    public static class Payload implements Serializable {
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

        public Payload(final String idHeader, final String kodeDokumen, final String nomorAju) {
            this.idHeader = idHeader;
            this.kodeDokumen = kodeDokumen;
            this.nomorAju = nomorAju;
        }
    }
}
