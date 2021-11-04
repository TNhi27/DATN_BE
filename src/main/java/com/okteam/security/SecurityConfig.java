package com.okteam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.okteam.exception.RestAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	 @Autowired
		MyUserDetailsService userService;
		
		@Bean
		public FilterSecurity jwtFilterRequestCustom() {
			return new FilterSecurity();
		}
		@Bean
	    public RestAuthenticationEntryPoint restServicesEntryPoint() {
	        return new RestAuthenticationEntryPoint();
	    }

		

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			// Get AuthenticationManager bean
			return super.authenticationManagerBean();
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
		}

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable();
			/*
			* Xu ly ngoai le khi chua xac thuc
			*/
			http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());

			/*
			* Ngan khong tao seesion
			*/
			
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
			
	        http.authorizeRequests().antMatchers("/login/**","/api/**").permitAll().antMatchers("/admin/**").hasRole("admin").anyRequest().authenticated();
			http.addFilterBefore(jwtFilterRequestCustom(), UsernamePasswordAuthenticationFilter.class);
	    }
}
