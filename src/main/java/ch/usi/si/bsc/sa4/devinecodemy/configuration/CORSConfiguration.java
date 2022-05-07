package ch.usi.si.bsc.sa4.devinecodemy.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS Configuration allows cross-domain configuration.
 */
@Configuration
@EnableWebMvc
public class CORSConfiguration implements WebMvcConfigurer {

    /**
     * Adds mappings and their specifications to the given registry.
     * @param registry the registry to add the mappings.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                .maxAge(-1)   // add maxAge
                .allowCredentials(false);
    }

}
