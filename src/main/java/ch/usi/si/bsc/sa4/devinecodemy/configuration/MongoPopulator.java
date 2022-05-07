package ch.usi.si.bsc.sa4.devinecodemy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

/**
 * Class to populate the MongoDB from a JSON file.
 */
@Configuration
public class MongoPopulator {
	/**
	 * Populates the db with the data contained in level-data.json
	 * @return the Jackson2RepositoryPopulatorFactoryBean.
	 */
	@Bean
	public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() {
		final Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
		factory.setResources(new Resource[] { new ClassPathResource("level-data.json") });
		return factory;
	}
}
