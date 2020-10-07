package eu.arima.tr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class WorklogsBase {
	@Autowired
	WebApplicationContext context;

	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.webAppContextSetup(this.context);
	}

}