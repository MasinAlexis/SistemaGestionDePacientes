package com.sgp.sistemaDeGestionDePacientes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Para este metodo no usar el nombre configureGlobal y @Autowired
    //Genera un error al cargar el bean, usar otro nombre y la notacion @Override
    @Override
    public void configure(AuthenticationManagerBuilder build) throws Exception{
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.headers().frameOptions().sameOrigin()
                .and()
                    .authorizeRequests()
                    .antMatchers("/css/**","/js/**","/img/**","/webjars/**","/scss/**","/vendor/**" )
                    .permitAll()
                    .antMatchers("/**").authenticated()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/listadoDePacientes")
                    .loginPage("/inicioSesion")
                    .loginProcessingUrl("/logincheck")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureUrl("/inicioSesion?error=true")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/inicioSesion")
                    .permitAll();
    }
}
