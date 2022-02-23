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

## How to Test the service in Local Development mode

1. `cd` into the root project directory
2. Run all dependent services: `docker-compose up`
3. Run the `scs-writer` service by `cd`-ing into the `scs-writer` folder and execute this command : `mvn spring-boot:run`.

## Execute All Unit Tests + Integration Tests

1. `cd` into the root project directory
2. `mvn test`
