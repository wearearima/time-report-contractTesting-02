package eu.arima.tr.reports;

import static eu.arima.tr.reports.DayStatus.EXTRA_HOURS;
import static eu.arima.tr.reports.DayStatus.MISSING_HOURS;
import static eu.arima.tr.reports.DayStatus.RIGHT_HOURS;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ReportsService {

	private final WebClient webClient;

	public ReportsService(WebClient webClient) {
		this.webClient = webClient;
	}

	public DayStatusSummary getDayStatusSummaryForWorkerAndDay(String workerUserName, LocalDate date) {
		// retrieve worklogs for worker and day
		List<WorklogInfo> worklogsForDay = webClient.get().uri(uriBuilder -> uriBuilder
				.path("/worklogs/worker/{workerUserName}").queryParam("day", date).build(workerUserName)).retrieve()
				.bodyToFlux(WorklogInfo.class).collectList().block();

		int totalDuration = worklogsForDay.stream().mapToInt(WorklogInfo::getDuration).sum();

		DayStatusSummary status = new DayStatusSummary(date, workerUserName);

		if (totalDuration == 8) {
			status.addDayStatus(RIGHT_HOURS);
		} else if (totalDuration > 8) {
			status.addDayStatus(EXTRA_HOURS);
		} else {
			status.addDayStatus(MISSING_HOURS);
		}

		return status;
	}

}
