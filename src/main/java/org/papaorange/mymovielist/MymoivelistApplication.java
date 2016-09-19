package org.papaorange.mymovielist.service;

import org.papaorange.mymovielist.controller.GetMyMovieListController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = GetMyMovieListController.class)

public class MymoivelistApplication {

	public static void main(String[] args) {
		SpringApplication.run(MymoivelistApplication.class, args);
	}
}
