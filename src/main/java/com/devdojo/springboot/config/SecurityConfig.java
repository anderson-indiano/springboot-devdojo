/**
 * 
 */
package com.devdojo.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.devdojo.springboot.service.CustomUserDetailService;

/**
 * @author Anderson Indiano on 7 de fev. de 2021.
 *
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailService cuds;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/*/protected/**").hasAnyRole("USER","ADMIN")
		.antMatchers("/*/admin/**").hasRole("ADMIN")
		.and()
		.httpBasic()
		.and()
		.csrf()
		.disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(cuds).passwordEncoder(new BCryptPasswordEncoder());
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		auth.inMemoryAuthentication()
//				.withUser("anderson").password(encoder.encode("123")).roles("USER").and()
//				.withUser("admin").password(encoder.encode("123")).roles("USER", "ADMIN");
//	}

}
