package it.sia.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = { "it.sia" })
@EnableScheduling
public class JDGHealthMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JDGHealthMonitorApplication.class, args);
	}

}
