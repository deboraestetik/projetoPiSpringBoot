package com.projetopi.tlgne.config;


import com.projetopi.tlgne.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder((passwordEncoder()));
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return  super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/produtos/**").permitAll()
                .antMatchers( "/imagens/**").permitAll()
                .antMatchers(HttpMethod.POST, "/produtos").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/produtos").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/produtos/**").hasAnyRole("ADMIN")
                .antMatchers("/usuarios").hasAnyRole("ADMIN")
                .antMatchers("/cliente").permitAll()
                .anyRequest().authenticated().and().httpBasic();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
