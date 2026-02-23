package auca.com.question5_task_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Question5TaskApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Question5TaskApiApplication.class, args);
	}

}
