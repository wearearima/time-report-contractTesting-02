package eu.arima.tr.reports;

import static eu.arima.tr.reports.DayStatus.MISSING_HOURS;
import static eu.arima.tr.reports.DayStatus.RIGHT_HOURS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@ExtendWith(MockitoExtension.class)
public class ReportsServiceWithMockWebServerTest {

	private static final LocalDate DAY = LocalDate.now();
	private static final String USERNAME = "USU";

	private MockWebServer mockWebServer;
	private ReportsService reportsService;

	@BeforeEach
	public void setup() throws IOException {
		this.mockWebServer = new MockWebServer();
		this.mockWebServer.start();
		this.reportsService = new ReportsService(
				WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build());
	}

	@Test
	@DisplayName("Given a worklog with 8 hours duration the status is RIGHT_HOURS")
	void when_the_worklog_for_the_resquested_day_is_8_hours_the_status_is_RIGHT_HOURS() throws InterruptedException {
		MockResponse mockResponse = new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("[{\"fromTime\": \"08:30:00\", \"toTime\": \"16:30:00\"}]");
		mockWebServer.enqueue(mockResponse);

		DayStatusSummary result = reportsService.getDayStatusSummaryForWorkerAndDay(USERNAME, DAY);

		assertWebClientRequestEquals("/worklogs/worker/" + USERNAME + "?day=" + DAY);
		assertStatusEquals(RIGHT_HOURS, result);

	}

	@Test
	@DisplayName("Given a list of worklogs with 5,1,1 hours duration the status is MISSING_HOURS")
	void when_the_worklogs_for_resquested_day_are_5_1_1_hours_the_status_is_MISSING_HOURS()
			throws InterruptedException {
		MockResponse mockResponse = new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("[{\"fromTime\": \"08:30:00\", \"toTime\": \"13:30:00\"},"
						+ "{\"fromTime\": \"14:30:00\", \"toTime\": \"15:30:00\"},"
						+ "{\"fromTime\": \"15:30:00\", \"toTime\": \"16:30:00\"}]");
		mockWebServer.enqueue(mockResponse);

		DayStatusSummary result = reportsService.getDayStatusSummaryForWorkerAndDay(USERNAME, DAY);

		assertWebClientRequestEquals("/worklogs/worker/" + USERNAME + "?day=" + DAY);
		assertStatusEquals(MISSING_HOURS, result);

	}

	@Test
	@DisplayName("Given the username of a worker the status result has that username")
	void the_status_result_belongs_to_the_requested_worker() throws InterruptedException {
		MockResponse mockResponse = new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("[]");
		mockWebServer.enqueue(mockResponse);

		DayStatusSummary result = reportsService.getDayStatusSummaryForWorkerAndDay(USERNAME, DAY);

		assertWebClientRequestEquals("/worklogs/worker/" + USERNAME + "?day=" + DAY);
		assertEquals(USERNAME, result.getWorkerUserName());

	}

	@Test
	@DisplayName("Given a date the status result has that date")
	void the_status_result_belongs_to_the_requested_day() throws InterruptedException {
		MockResponse mockResponse = new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("[]");
		mockWebServer.enqueue(mockResponse);

		LocalDate day = DAY;
		DayStatusSummary result = reportsService.getDayStatusSummaryForWorkerAndDay(USERNAME, day);

		assertWebClientRequestEquals("/worklogs/worker/" + USERNAME + "?day=" + DAY);
		assertEquals(day, result.getDate());

	}

	private void assertWebClientRequestEquals(String requestUrl) throws InterruptedException {
		RecordedRequest request = mockWebServer.takeRequest();
		assertEquals(requestUrl, request.getPath());
	}

	private void assertStatusEquals(DayStatus dayStatus, DayStatusSummary result) {
		assertEquals(1, result.getStatusList().size());
		assertEquals(dayStatus, result.getStatusList().get(0));
	}

}
