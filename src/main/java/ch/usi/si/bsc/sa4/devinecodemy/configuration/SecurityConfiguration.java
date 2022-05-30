package ch.usi.si.bsc.sa4.devinecodemy.configuration;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Security Configuration: Setting up which routes can be accessed without
 * being authenticated or with authentication.
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
        httpSecurity.authorizeRequests()
                .antMatchers("/auth/check","/auth/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/auth/login", true)
                .and()
                .logout()
                .logoutSuccessUrl("/auth/logout")
                .and().csrf().disable();
    }
}
