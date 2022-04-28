package ch.usi.si.bsc.sa4.devinecodemy;

import ch.usi.si.bsc.sa4.devinecodemy.configuration.CustomJackson2RepositoryPopulatorFactoryBean;
import ch.usi.si.bsc.sa4.devinecodemy.repository.LevelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.AbstractRepositoryPopulatorFactoryBean;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@SpringBootApplication
public class DevineCodemyBackend {

	public static void main(String[] args) {
		SpringApplication.run(DevineCodemyBackend.class, args);
	}
	
	@Bean
	public AbstractRepositoryPopulatorFactoryBean repositoryPopulator(ObjectMapper objectMapper, LevelRepository levelRepository) {
		Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
		levelRepository.deleteAll();
		factory.setMapper(objectMapper);
		factory.setResources(new Resource[]{new ClassPathResource("level-data.json")});
		return factory;
	}

}
