package ch.usi.si.bsc.sa4.lab02spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;

@SpringBootApplication
public class Lab02SpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lab02SpringApplication.class, args);
	}

}
