package com.springproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LectureEvaluationSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(LectureEvaluationSpringApplication.class, args);
	}

}
