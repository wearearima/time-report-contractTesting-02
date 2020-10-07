package eu.arima.tr.reports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = { "server.url=http://localhost:8083" })
@AutoConfigureStubRunner(ids = { "eu.arima.tr:timeReports-producer:+:stubs:8083" })
public class ReportsServiceContractTest {

	@Autowired
	private ReportsService reportsService;

	@Test
	void test_getDayStatusSummaryForWorkerAndDay() {
		LocalDate dateFromProducerTest = LocalDate.now();
		String workerFromProducerTest = "TestUser";
		DayStatusSummary result = reportsService.getDayStatusSummaryForWorkerAndDay(workerFromProducerTest,
				dateFromProducerTest);
		assertEquals(workerFromProducerTest, result.getWorkerUserName());
		assertEquals(dateFromProducerTest, result.getDate());
	}

}