package com.userLogin.security;

import com.userLogin.security.filter.JwtRequestFilter;
import com.userLogin.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .authorizeRequests().antMatchers("/api/public/authenticate").permitAll()
                .antMatchers("/api/public/user/**").permitAll()
                .antMatchers("/api/items/**").permitAll()
                .antMatchers("/api/public/order/**").permitAll()
                .antMatchers("/api/public/user/add-item-to-order/{userId}/{orderId}/{itemId}").permitAll()
                .antMatchers("/api/public/user/remove-item-from-order/{userId}/{orderId}/{itemId}").permitAll()
                .antMatchers("/api/public/user/orders/{userId}}").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/public/order/{orderId}").permitAll()
                .antMatchers("/api/public/user/get/{id}").permitAll()
                .antMatchers("/api/items/{itemId}").permitAll()
                .antMatchers("/api/public/user/**").permitAll()
                .antMatchers("/api/public/user/add-favorite/{userId}/{itemId}").permitAll()
                .antMatchers("/api/public/user//favorite-items/{userId}").permitAll()
                .antMatchers("/api/public/user/remove-favorite/{userId}/{itemId}").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}


