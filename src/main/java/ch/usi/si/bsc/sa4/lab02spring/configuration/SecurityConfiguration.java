package ch.usi.si.bsc.sa4.lab02spring.configuration;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Security Configuration: Seting up which routes can be accessed without
 * being authenticated or with authentiction.
 * **/
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Sets the configuration of the Security to avoid unauthenticated users
     * to access any route
     * @param httpSecurity httpSecurity setter
     * @throws Exception Exceptions thrown on gitlab login errors
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//                httpSecurity.authorizeRequests().anyRequest().permitAll();
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/users/login", true)
                .and().csrf().disable();
    }
}
