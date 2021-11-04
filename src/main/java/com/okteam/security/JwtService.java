package com.okteam.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtService {
	static final long EXPIRATIONTIME = 86400000; // 1 day
	static final String SECRET = "pc00653xxxxxxxxx";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	public static String generateJwt(String username) {
		long expirationTime = EXPIRATIONTIME;
		return Jwts.builder().setId(username).setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}
	
	public static Authentication getAuthentication(HttpServletRequest req) {
		String token = req.getHeader(HEADER_STRING);
		if (token!=null) {
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			String username = claims.getId();
			return new UsernamePasswordAuthenticationToken(username, "");
		}
		return null;
		
	}
	
	public static String getUsernameFromJwt(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();
		String username = claims.getId();
		return username;
	}
	
	 public boolean validateToken(String authToken) {
	        try {
	            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
	            return true;
	        } catch (MalformedJwtException ex) {
	        	ex.printStackTrace();
	            System.out.println("Invalid JWT token");
	        } catch (ExpiredJwtException ex) {
	        	ex.printStackTrace();
	            System.out.println("Expired JWT token");
	   
	        } catch (UnsupportedJwtException ex) {
	        	ex.printStackTrace();
	            System.out.println("Unsupported JWT token");
	            
	        } catch (IllegalArgumentException ex) {
	        	ex.printStackTrace();
	            System.out.println("JWT claims string is empty.");
	          
	        }
	        return false;
	    }
	 
	 public long getExpirationTime() {
		return EXPIRATIONTIME;
	}
}
