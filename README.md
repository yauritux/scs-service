# scs-service

SCS stands for `Single Core Service` is one of the Bea Cukai's **reactive service** within it's microservice ecosystem that responsible to handle various document operations.
It's undisputed that **Document** is something that Bea Cukai deals day-to-day, hence it's become part of the **core application services**. 
This particular `scs-service` is built using some latest Java technologies as following:
- **Java 11**.
- **Spring 5**, with embedded **projectreactor** to leverage the reactive streaming behavior.
- **Spring R2DBC**, i.e. Reactive Relational Database Connectivity to ensure all database operations won't be blocking the Thread (a.k.a. *non-blocking*).

Other stacks being used (wrapped inside docker-compose):
- PostgreSQL version 14 for both writer and reader databases.
- Confluent Kafka (along with Zookeeper and other stuffs such as kafka-connect, etc) 

The architecture itself is based on the **EDA** (`Event-Driven Architecture`) which also embrace the **CQRS** (`Command-Query-Responsibility-Segregation`) pattern to give a separation-of-concerns between the **Command** (a.k.a. `writer service`) and the **Query** (a.k.a. `reader service`).
Therefore, it gives us more freedom to scale-up independently those services. For instance, if the reading traffic from users were found to be more intensive than the writing operation, then we can scale-up the `reader service` independently from the `writer service`. 
Another benefit from this kind of **CQRS** pattern is that we can distribute the load among writer and reader databases, hence we won't burden our databases.

## How to Run the service in a Local Development mode

1. `cd` into the root project directory
2. Run all dependent services: `docker-compose up`
3. Run the `scs-writer` service by `cd`-ing into the `scs-writer` folder and execute this command : `mvn spring-boot:run`.

## Test Endpoints

### Create a new Document Header

```
curl -X POST -H "Content-Type: application/json" -d '{"asalData": "W", "idPerusahaan": "1234567890", "namaPerusahaan": "PT. DEMO PORTAL", "roleEntitas": "IMPORTIR", "kodeDokumen": "20", "userPortal": "yauritux"}' localhost:8080/v2/headers
```

You'll get a response more or less as follow:

`HTTP STATUS: 201 (Created)`

```
{
  "idHeader": "6591df5f-be4e-4424-8338-8ab83eab0b5b",
  "asalData": "W",
  "asuransi": null,
  "biayaPengurang": null,
  "biayaTambahan": null,
  "bruto": null,
  "cif": null,
  "dasarPengenaanPajak": null,
  "disclaimer": null,
  "email": null,
  "flagCurah": false,
  "flagMigas": false,
  "flagPph": false,
  "flagSda": false,
  "flagVd": false,
  "fob": null,
  "freight": null,
  "hargaPenyerahan": null,
  "hargaPerolehan": null,
  "idPelmuatAkhir": null,
  "idPengguna": null,
  "idPerusahaan": "555777888999",
  "idPpjk": null,
  "jabatanTtd": null,
  "jatuhTempoBilling": null,
  "jumlahBruto": null,
  "jumlahCif": null,
  "jumlahFob": null,
  "jumlahHargaPenyerahan": null,
  "jumlahKontainer": null,
  "jumlahNetto": null,
  "jumlahNilaiBarang": null,
  "jumlahNilaiVd": null,
  "jumlahTandaPengaman": null,
  "jumlahVolume": null,
  "kodeAsalBarangFtz": null,
  "kodeAsuransi": null,
  "kodeBank": null,
  "kodeBilling": null,
  "kodeCaraAngkutPlb": null,
  "kodeCaraBayar": null,
  "kodeCaraDagang": null,
  "kodeDaerahAsal": null,
  "kodeDokumen": "20",
  "kodeFaktur": null,
  "kodeGudangAsal": null,
  "kodeGudangTujuan": null,
  "kodeIncoterm": null,
  "kodeJenisEkspor": null,
  "kodeJenisImpor": null,
  "kodeJenisKirim": null,
  "kodeJenisNilai": null,
  "kodeJenisPengiriman": null,
  "kodeJenisPlb": null,
  "kodeJenisProsedur": null,
  "kodeJenisTandaPengaman": null,
  "kodeJenisTpb": null,
  "kodeKantor": null,
  "kodeKantorBongkar": null,
  "kodeKantorEkspor": null,
  "kodeKantorMuat": null,
  "kodeKantorPeriksa": null,
  "kodeKantorTujuan": null,
  "kodeKategoriBarangFtz": null,
  "kodeKategoriEkspor": null,
  "kodeKategoriKeluarFtz": null,
  "kodeKategoriMasukFtz": null,
  "kodeKenaPajak": null,
  "kodeLokasi": null,
  "kodeLokasiBayar": null,
  "kodeNegaraTujuan": null,
  "kodePelBongkar": null,
  "kodePelEkspor": null,
  "kodePelMuat": null,
  "kodePelTransit": null,
  "kodePelTujuan": null,
  "kodePembayar": null,
  "kodeTps": null,
  "kodeTujuanPemasukan": null,
  "kodeTujuanPengiriman": null,
  "kodeTujuanTpb": null,
  "kodeTutupPu": null,
  "kodeValuta": null,
  "kodeTtd": null,
  "lokasiAsal": null,
  "lokasiTujuan": null,
  "namaCaraBayar": null,
  "namaIncoterm": null,
  "namaJenisImpor": null,
  "namaJenisNilai": null,
  "namaJenisProsedur": null,
  "namaJenisTpb": null,
  "namaKantorBongkar": null,
  "namaKantorMuat": null,
  "namaKantorPendek": null,
  "namaKantorTujuan": null,
  "namaPelabuhanBongkar": null,
  "namaPelabuhanMuat": null,
  "namaPelabuhanTujuan": null,
  "namaPerusahaan": "PT. DEMO PORTAL",
  "namaPpjk": null,
  "namaProses": null,
  "namaTransaksiLainnyaFtz": null,
  "namaTtd": null,
  "namaValuta": null,
  "ndpbm": null,
  "netto": null,
  "nik": null,
  "nilaiBarang": null,
  "nilaiIncoterm": null,
  "nilaiJasa": null,
  "nilaiKurs": null,
  "nilaiMaklon": null,
  "nipRekam": null,
  "nomorAju": "00002055577720220223000001",
  "nomorBc11": null,
  "nomorBuktiBayar": null,
  "nomorDaftar": null,
  "npppjk": null,
  "npwpBilling": null,
  "npwpPemusatan": null,
  "posBc11": null,
  "roleEntitas": "IMPORTIR",
  "seri": 0,
  "subposBc11": null,
  "tanggalAju": null,
  "tanggalBc11": null,
  "tanggalBerangkat": null,
  "tanggalBilling": null,
  "tanggalBuktiBayar": null,
  "tanggalDaftar": null,
  "tanggalEkspor": null,
  "tanggalIjinPenerima": null,
  "tanggalIjinPengusaha": null,
  "tanggalMasuk": null,
  "tanggalMuat": null,
  "tanggalNpppjk": null,
  "tanggalPeriksa": null,
  "tanggalStuffing": null,
  "tglAkhirBerlaku": null,
  "tglAwalBerlaku": null,
  "totalDanaSawit": null,
  "ttBarang": null,
  "uangMuka": null,
  "urlDokumen": null,
  "userPortal": "yauritux",
  "vd": null,
  "versiModul": null,
  "volume": null
}
```

### Update an existing Document Header

```
curl -X PUT -H "Content-Type: application/json" -d '{"jumlahKontainer": 100, "jumlahNilaiBarang": 95000000.00, "lokasiAsal": "Singapore", "lokasiTujuan": "Jakarta"}' localhost:8080/v2/headers/6591df5f-be4e-4424-8338-8ab83eab0b5b
```

You'll get a response more or less as follow:

`HTTP STATUS: 200 (OK)` (i.e. if idHeader is an existing record in the database)

OR

`HTTP STATUS: 404 (NOT FOUND)` (i.e. if idHeader is not found in the database)

## Execute Unit Tests

1. `cd` into the root project directory
2. `mvn clean test`

## Execute Integration Tests

1. `cd` into the root project directory
2. `mvn clean verify`

## Check Code Test Coverage

In order to see how much your code have been covered by the automation tests, you can open the file `scs-writer/target/site/jacoco/index.html` in your browser.
