package com.okteam.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


public class FilterSecurity extends OncePerRequestFilter {
	@Autowired
	JwtService jwtService;
	
	@Autowired
	MyUserDetailsService service;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
			String jwt = this.getJwtFromRequest(request);
			if (StringUtils.hasText(jwt)&& jwtService.validateToken(jwt)) {
				String username = jwtService.getUsernameFromJwt(jwt);
				UserDetails userDetails =service.loadUserByUsername(username);
				if (userDetails!=null) {
					UsernamePasswordAuthenticationToken auth = 
							new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
			filterChain.doFilter(request, response);
	}

	public String  getJwtFromRequest(HttpServletRequest req) {
		String s = req.getHeader("Authorization");
		if (StringUtils.hasText(s)&& s.startsWith("Bearer ")) {
			return s.substring(7,s.length());
		}
		return null;
	}

	
}
