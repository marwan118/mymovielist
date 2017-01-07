package org.papaorange.moviedbbuilderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MymoviedbbuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MymoviedbbuilderApplication.class, args);
	}
}
