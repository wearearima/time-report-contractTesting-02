package eu.arima.tr.reports;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorklogInfo {

	private String taskName;
	private LocalTime fromTime;
	private LocalTime toTime;
	private String description;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public LocalTime getFromTime() {
		return fromTime;
	}

	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}

	public LocalTime getToTime() {
		return toTime;
	}

	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return (int) fromTime.until(toTime, ChronoUnit.HOURS);
	}

	@JsonProperty("task")
	private void unpackNested(Map<String, Object> task) {
		this.taskName = (String) task.get("name");
	}
}
