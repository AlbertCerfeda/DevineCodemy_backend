package ch.usi.si.bsc.sa4.lab02spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // TODO: Possibly remove when implementing authentication in the project.
        httpSecurity.authorizeRequests()
                .anyRequest().permitAll()
                // To allow requests other than GET to be handled without authentication
                .and().csrf().disable();
    }
}