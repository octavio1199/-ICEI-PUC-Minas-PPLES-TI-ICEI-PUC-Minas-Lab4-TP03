package com.main.sistema_moedas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*Descomentar as linhas abaixo para habilitar*/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	ImplementsUserDetailService userDetailService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests().antMatchers("/").permitAll()
				.antMatchers(HttpMethod.GET, "/usuario/new").permitAll()
				.antMatchers(HttpMethod.POST, "/aluno/new").permitAll()
				.antMatchers(HttpMethod.POST, "/empresa/new").permitAll()
				.antMatchers("/usuario").hasRole("ADMIN")
				.antMatchers("/aluno/").hasRole("ALUNO")
				.antMatchers("/empresa/").hasRole("EMPRESA")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll()
				.and().logout().logoutSuccessUrl("/").logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}

}
