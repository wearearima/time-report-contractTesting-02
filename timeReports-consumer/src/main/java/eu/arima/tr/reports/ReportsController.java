package eu.arima.tr.reports;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/reports")
public class ReportsController {

	private ReportsService reportsService;

	public ReportsController(ReportsService reportsService) {
		this.reportsService = reportsService;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			}

			@Override
			public String getAsText() throws IllegalArgumentException {
				return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDate) getValue());
			}
		});
	}

	@GetMapping
	public String index() {
		return "reports/reportList";
	}

	@GetMapping("/report-worker-day")
	public String showReportForWorkerAndDayForm(Model model) {
		model.addAttribute(new WorkerDayReportForm());
		return "reports/reportForWorkerDay";
	}

	@PostMapping("/report-worker-day")
	public Mono<String> generateReportForForWorkerAndDay(@ModelAttribute WorkerDayReportForm form, Model model,
			RedirectAttributes redirectAttrs) {
		DayStatusSummary dayStatusSummary = reportsService.getDayStatusSummaryForWorkerAndDay(form.getWorkerUserName(),
				form.getDate());
		model.addAttribute("workerDayReportForm", form);
		model.addAttribute("dayStatusSummary", dayStatusSummary);
		return Mono.just("reports/reportForWorkerDay");
	}

}
