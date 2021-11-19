package com.okteam.restcontroller;

import com.okteam.entity.UserLoginResponse;
import com.okteam.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class LoginController {

    @Autowired
    AuthenticationManager authentication;
    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> Login(@RequestParam String username, @RequestParam String password) {
        Authentication auth = authentication.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtService.generateJwt(username);

        UserLoginResponse rs = new UserLoginResponse();
        rs.setUsername(username);
        rs.setRole(auth.getAuthorities().toString());
        rs.setToken(jwt);

        return new ResponseEntity<UserLoginResponse>(rs, HttpStatus.OK);

    }
}