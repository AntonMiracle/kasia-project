package com.kasia.configuration;

import com.kasia.model.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.kasia.controller.ViewAndURLController.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(U_ROOT, U_LOGIN, U_REGISTRATION, "/css/**","/images/**", "/js/**").permitAll()
                .antMatchers("/**").hasRole(Role.USER.toString())
                .and()
                .formLogin()
                .loginPage(U_LOGIN)
                .defaultSuccessUrl(U_HOME)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
