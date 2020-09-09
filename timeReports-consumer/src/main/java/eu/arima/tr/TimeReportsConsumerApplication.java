package eu.arima.tr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class TimeReportsConsumerApplication {

	@Value("${server.url}")
	String url;

	@Bean
	WebClient webClient() {
		return WebClient.builder().baseUrl(url).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(TimeReportsConsumerApplication.class, args);
	}

}
