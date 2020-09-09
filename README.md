App for creating reports of worklogs. Will be used as an example app for testing purposes. It consists of two parts: consumer and producer.

**Status**

* Use case: Generate a report for one user and one day (WIP).  
When the user sets a worker's userName and selects a date the application should show the status of worklogs for that user/date.

To run the app

Run de producer:

```
 cd timeReports-producer
 ./mvnw spring-boot:run
```

Run de consumer:

```
 cd timeReports-consumer
 ./mvnw spring-boot:run
```

And go to the [Home page at http://localhost:8080](http://localhost:8080) (the data in the database is the one contained in this [file](https://github.com/jaguado-arima/time-report-contractTesting/blob/master/timeReports-producer/src/main/resources/db/h2/data.sql))
