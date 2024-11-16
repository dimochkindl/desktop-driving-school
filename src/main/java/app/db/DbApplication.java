package app.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbApplication {

	public static void main(String[] args) {
		// Start the Spring Boot application
		SpringApplication.run(DbApplication.class, args);
		// Note: Do not call launch(args) here
	}
}