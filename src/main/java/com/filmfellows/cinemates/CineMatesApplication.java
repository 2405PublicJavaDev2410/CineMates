package com.filmfellows.cinemates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CineMatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CineMatesApplication.class, args);
	}

}
