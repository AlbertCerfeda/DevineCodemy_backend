package ch.usi.si.bsc.sa4.devinecodemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starts the SpringBoot backend server.
 */
@SpringBootApplication
public class DevineCodemyBackend {

	/**
	 * Starts the backend Spring application.
	 * @param args command-line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DevineCodemyBackend.class, args);
	}

}
