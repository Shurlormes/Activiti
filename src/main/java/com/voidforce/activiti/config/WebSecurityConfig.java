package com.voidforce.activiti.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //@Autowired
    //private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//.addFilterBefore(corsFilter, SessionManagementFilter.class)
	        .authorizeRequests()
            .antMatchers("/post", "/put", "/delete", "/get").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/").permitAll()
            .defaultSuccessUrl("/")
            .and()
            .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(customUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
