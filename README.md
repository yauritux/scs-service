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
curl -X POST -H "Content-Type: application/json" -d '{"asalData": "W", "idPerusahaan": "1234567890", "namaPerusahaan": "PT. DEMO PORTAL", "roleEntitas": "IMPORTIR", "kodeDokumen": "20", "namaProses": "TESTING_PROSES", "kodeIncoterm": "1", "namaIncoterm": "TESTING", "kodeCaraBayar": "1", "namaCaraBayar": "CASH", "userPortal": "yauritux"}' localhost:8080/v2/headers
```

You should get a 201 response for a success operation.

### Update an existing Document Header

```
curl -X PUT -H "Content-Type: application/json" -d '{"jumlahKontainer": 100, "jumlahNilaiBarang": 95000000.00, "lokasiAsal": "Singapore", "lokasiTujuan": "Jakarta"}' localhost:8080/v2/headers/6591df5f-be4e-4424-8338-8ab83eab0b5b
```

You should get a 200 response (i.e. successful update) or 404 (update failed due to non-existing idHeader).

## Execute Unit Tests

1. `cd` into the root project directory
2. `mvn clean test`

## Execute Integration Tests

1. `cd` into the root project directory
2. `mvn clean verify`

## Check Code Test Coverage

In order to see how much your code have been covered by the automation tests, you can open the file `scs-writer/target/site/jacoco/index.html` in your browser.
