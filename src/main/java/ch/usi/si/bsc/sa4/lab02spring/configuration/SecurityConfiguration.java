package ch.usi.si.bsc.sa4.lab02spring.configuration;

import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeRequests(a -> a
//                    .antMatchers("/", "/login", "/loginFailure", "/error", "/webjars/**").permitAll()
//                    .anyRequest().authenticated()
//                )
//                .exceptionHandling(e -> e
//                        .accessDeniedPage("/loginFailure")
//                )
//                .oauth2Login(o -> o
//                        .failureHandler((request, response, exception) -> {
//                                    request.getSession().setAttribute("error.message", exception.getMessage());
//                                    response.sendError(HttpStatus.UNAUTHORIZED.ordinal(), exception.getMessage());
//                                }
//                        )
//                        .failureUrl("/error")
//                        .defaultSuccessUrl("/loginSuccess")
//                );

        //        httpSecurity.authorizeRequests().anyRequest().permitAll();
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated().and().oauth2Login()
                .and().csrf().disable();
    }
}