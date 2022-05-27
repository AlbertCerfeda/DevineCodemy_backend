package ch.usi.si.bsc.sa4.devinecodemy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CORSConfiguration {
	@Value("${codeland.frontend.url}")
	private String frontendUrl;

	// This has to be a normal configuration class,
    // not extending WebMvcConfigurerAdapter or other Spring Security class

    @Bean
    public FilterRegistrationBean<CorsFilter> customCorsFilter() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);

        // We allow requests from our front-end url,
        // you should probably move this to an environment variable
        config.addAllowedOrigin(frontendUrl);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        var bean = new FilterRegistrationBean<>(new CorsFilter(source));

        // Here where we tell Spring to load this filter at the right point in the chain
        // (with an order of precedence higher than oauth2's filters)
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
