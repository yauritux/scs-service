package id.go.beacukai.scswriter.application.port.incoming.converter;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import id.go.beacukai.scs.sharedkernel.domain.event.HeaderUpdatedEvent;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static id.go.beacukai.scs.sharedkernel.domain.event.HeaderUpdatedEvent.Payload;

@ReadingConverter
public class HeaderUpdatedEventReadConverter implements Converter<Row, HeaderUpdatedEvent> {

    @Override
    public HeaderUpdatedEvent convert(Row source) {
        Payload eventPayload = new Payload();

        eventPayload.setIdHeader(source.get("id_header", String.class));
        eventPayload.setAsalData(source.get("asal_data", String.class));
        eventPayload.setAsuransi(source.get("asuransi", BigDecimal.class));
        eventPayload.setBiayaPengurang(source.get("biaya_pengurang", BigDecimal.class));
        eventPayload.setBiayaTambahan(source.get("biaya_tambahan", BigDecimal.class));
        eventPayload.setBruto(source.get("bruto", BigDecimal.class));
        eventPayload.setCif(source.get("cif", String.class));
        eventPayload.setDasarPengenaanPajak(source.get("dasar_pengenaan_pajak", String.class));
        eventPayload.setDisclaimer(source.get("disclaimer", String.class));
        eventPayload.setEmail(source.get("email", String.class));
        eventPayload.setFlagCurah(Boolean.TRUE.equals(source.get("flag_curah", Boolean.class)));
        eventPayload.setFlagMigas(Boolean.TRUE.equals(source.get("flag_migas", Boolean.class)));
        eventPayload.setFlagPph(Boolean.TRUE.equals(source.get("flag_pph", Boolean.class)));
        eventPayload.setFlagSda(Boolean.TRUE.equals(source.get("flag_sda", Boolean.class)));
        eventPayload.setFlagVd(Boolean.TRUE.equals(source.get("flag_vd", Boolean.class)));
        eventPayload.setFob(source.get("fob", String.class));
        eventPayload.setFreight(source.get("freight", Double.class));
        eventPayload.setHargaPenyerahan(source.get("harga_penyerahan", BigDecimal.class));
        eventPayload.setHargaPerolehan(source.get("harga_perolehan", BigDecimal.class));
        eventPayload.setIdPelmuatAkhir(source.get("id_pelmuat_akhir", String.class));
        eventPayload.setIdPengguna(source.get("id_pengguna", String.class));
        eventPayload.setIdPerusahaan(source.get("id_perusahaan", String.class));
        eventPayload.setIdPpjk(source.get("id_ppjk", String.class));
        eventPayload.setJabatanTtd(source.get("jabatan_ttd", String.class));
        eventPayload.setJatuhTempoBilling(source.get("jatuh_tempo_billing", LocalDate.class));
        eventPayload.setJumlahBruto(source.get("jumlah_bruto", BigDecimal.class));
        eventPayload.setJumlahCif(source.get("jumlah_cif", BigDecimal.class));
        eventPayload.setJumlahFob(source.get("jumlah_fob", BigDecimal.class));
        eventPayload.setJumlahHargaPenyerahan(source.get("jumlah_harga_penyerahan", BigDecimal.class));
        eventPayload.setJumlahKontainer(source.get("jumlah_kontainer", Integer.class));
        eventPayload.setJumlahNetto(source.get("jumlah_netto", Integer.class));
        eventPayload.setJumlahNilaiBarang(source.get("jumlah_nilai_barang", BigDecimal.class));
        eventPayload.setJumlahNilaiVd(source.get("jumlah_nilai_vd", BigDecimal.class));
        eventPayload.setJumlahTandaPengaman(source.get("jumlah_tanda_pengaman", Integer.class));
        eventPayload.setJumlahVolume(source.get("jumlah_volume", Double.class));
        eventPayload.setKodeAsalBarangFtz(source.get("kode_asal_barang_ftz", String.class));
        eventPayload.setKodeAsuransi(source.get("kode_asuransi", String.class));
        eventPayload.setKodeBank(source.get("kode_bank", String.class));
        eventPayload.setKodeBilling(source.get("kode_billing", String.class));
        eventPayload.setKodeCaraAngkutPlb(source.get("kode_cara_angkut_plb", String.class));
        eventPayload.setKodeCaraBayar(source.get("kode_cara_bayar", String.class));
        eventPayload.setKodeCaraDagang(source.get("kode_cara_dagang", String.class));
        eventPayload.setKodeDaerahAsal(source.get("kode_daerah_asal", String.class));
        eventPayload.setKodeDokumen(source.get("kode_dokumen", String.class));
        eventPayload.setKodeFaktur(source.get("kode_faktur", String.class));
        eventPayload.setKodeGudangAsal(source.get("kode_gudang_asal", String.class));
        eventPayload.setKodeGudangTujuan(source.get("kode_gudang_tujuan", String.class));

        var event = new HeaderUpdatedEvent(source.get("event_id", String.class));
        event.setEventType(source.get("event_type", String.class));
        event.setEventHandler(source.get("event_handler", String.class));
        var eventVersion = Optional.ofNullable(source.get("version", Long.class));
        eventVersion.ifPresent(event::setVersion);
        event.setEventReferenceId(source.get("event_reference_id", String.class));
        event.setData(eventPayload);
        event.setTimestamp(source.get("timestamp", LocalDateTime.class));
        event.setCreatedBy(source.get("created_by", String.class));

        return event;
    }
}
