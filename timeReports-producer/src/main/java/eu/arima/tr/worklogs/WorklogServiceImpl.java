package eu.arima.tr.worklogs;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class WorklogServiceImpl implements WorklogService {

	private final WorklogRepository worklogRepository;

	public WorklogServiceImpl(WorklogRepository worklogRepository) {
		this.worklogRepository = worklogRepository;
	}

	/*
	 * @Override public List<Worklog> getWorklogsForWorkerAndDay(String
	 * workerUserName, LocalDate date) {
	 * 
	 * List<Worklog> worklogsForDay =
	 * this.worklogRepository.findByUsernameAndDate(workerUserName, date);
	 * 
	 * int totalDuration =
	 * worklogsForDay.stream().mapToInt(Worklog::getDuration).sum();
	 * 
	 * DayStatusSummary status = new DayStatusSummary(date, workerUserName);
	 * 
	 * if (totalDuration == 8) { status.addDayStatus(RIGHT_HOURS); } else if
	 * (totalDuration > 8) { status.addDayStatus(EXTRA_HOURS); } else {
	 * status.addDayStatus(MISSING_HOURS); }
	 * 
	 * return status; }
	 */

	@Override
	public List<Worklog> getWorklogsForWorkerAndDay(String workerUserName, LocalDate date) {
		List<Worklog> worklogsForDay = this.worklogRepository.findByUsernameAndDate(workerUserName, date);
		return worklogsForDay;
	}
}
