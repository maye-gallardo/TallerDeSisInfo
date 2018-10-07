package com.teacherselection.teacherselectionservice.security;
import com.teacherselection.teacherselectionservice.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/admin/**").permitAll()
        .antMatchers("/api/**").permitAll()
        .and().httpBasic()
        .and().logout().logoutUrl("/api/logout").logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
        .and().sessionManagement().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider createDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
       builder.userDetailsService(userService);
    }
}
