package com.kasia.configuration;

import com.kasia.model.Role;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.kasia.controller.ViewNameAndControllerURL.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserService uService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(U_ROOT, U_LOGIN, U_REGISTRATION, "/css/**","/images/**","/favicon.ico", "/js/**").permitAll()
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
