package com.devdojo.springboot.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.devdojo.springboot.model.DBUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.devdojo.springboot.config.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("parametro " + request.getParameter("username"));
			DBUser user = new ObjectMapper().readValue(request.getInputStream(), DBUser.class);
			System.out.println("JWTAuthenticationFilter " + user.getUsername() + " " + user.getPassword());
			System.out.println("new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()) "
					+ new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			return this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (IOException e) {
			throw new RuntimeException(e);

		}
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((User) authResult.getPrincipal()).getUsername();
		String token = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		String bearerToken = TOKEN_PREFIX + token;
		response.getWriter().write(bearerToken); // coloca o token no body pra facilitar a vida do frontend
		response.addHeader(HEADER_STRING, bearerToken);
	}
}
