package eu.arima.tr.worklogs;

import java.time.LocalDate;
import java.util.List;

public interface WorklogService {

	/**
	 * Retrieves all worklogs for worker and date
	 * 
	 * @param workerUserName Workers username
	 * @param date           Date
	 * @return List of all worklogs
	 */
	List<Worklog> getWorklogsForWorkerAndDay(String workerUserName, LocalDate date);

}
