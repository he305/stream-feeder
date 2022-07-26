package com.github.he305.streamfeeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamFeederApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(StreamFeederApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

}
