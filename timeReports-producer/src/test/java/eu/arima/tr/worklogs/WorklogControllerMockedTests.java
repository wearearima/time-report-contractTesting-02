package eu.arima.tr.worklogs;

import static java.util.Collections.emptyList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class WorklogControllerMockedTests {

	private static final LocalDate DAY = LocalDate.now();
	private static final String USERNAME = "USU";

	private static final String CORRECT_URL = "/worklogs/worker/" + USERNAME + "?day=" + DAY;

	@MockBean
	WorklogServiceImpl worklogService;

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("It returns a list with existing worklogs for requested worker and day")
	void worklog_list_for_existing_user() throws Exception {
		Worklog worklog1 = createWorklogWithDescription(1, "Description");
		Worklog worklog2 = createWorklogWithDescription(2, "Another description");
		when(worklogService.getWorklogsForWorkerAndDay(USERNAME, DAY)).thenReturn(Arrays.asList(worklog1, worklog2));

		mvc.perform(get(CORRECT_URL).contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(worklog1.getId())))
				.andExpect(jsonPath("$[0].description", is("Description")))
				.andExpect(jsonPath("$[0].day", is(DAY.toString())))
				.andExpect(jsonPath("$[1].id", is(worklog2.getId())))
				.andExpect(jsonPath("$[1].description", is("Another description")))
				.andExpect(jsonPath("$[1].day", is(DAY.toString())));

	}

	@Test
	@DisplayName("When the user doesn't have worklogs it returns an empty list")
	void no_worklogs_for_user() throws Exception {

		when(worklogService.getWorklogsForWorkerAndDay(USERNAME, DAY)).thenReturn(emptyList());

		mvc.perform(get(CORRECT_URL).contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

	}

	@Test
	@DisplayName("When no date is request it returns status 400")
	void response_400_for_request_with_no_date() throws Exception {

		mvc.perform(get("/worklogs/worker/" + USERNAME).contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	private Worklog createWorklogWithDescription(Integer id, String description) {
		Worklog worklog = new Worklog();
		worklog.setId(id);
		worklog.setDay(DAY);
		worklog.setDescription(description);
		return worklog;
	}

}
