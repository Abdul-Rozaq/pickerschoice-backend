package com.pickerschoice.pickerschoice.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickerschoice.pickerschoice.dto.LoginRequest;
import com.pickerschoice.pickerschoice.exception.AppAuthException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManger;
	
	// this method authenticate our user name and password
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException 
	{
		try {
			LoginRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()
			);
			
			Authentication authenticate = authenticationManger.authenticate(authentication);
			return authenticate;
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
			throw new AppAuthException(e.getMessage());
		}
	}

    // generate a token for the client
    // this works only after the above method is successful
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException 
	{
		String key = "SecuredKeySecuredKeySecuredKeySecuredKeySecuredKeySecuredKeySecuredKeySecuredKeySecuredKey";
		String token = Jwts.builder()
							.setSubject(authResult.getName())
							.claim("authorities", authResult.getAuthorities())
							.setIssuedAt(new Date())
							.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
							.signWith(Keys.hmacShaKeyFor(key.getBytes()))
							.compact();
		
		SecurityContextHolder.getContext().setAuthentication(authResult);
		response.setHeader("Access-Control-Expose-Headers", "Authorization");	
		response.addHeader("Authorization", token);
	}
}
