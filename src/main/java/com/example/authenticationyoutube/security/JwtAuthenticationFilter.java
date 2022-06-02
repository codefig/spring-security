package com.example.authenticationyoutube.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authenticationyoutube.UserPrincipal;
import com.example.authenticationyoutube.model.LoginViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationCredentialsNotFoundException {
        LoginViewModel credentials = null;
        try{
             credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>()
        );
        Authentication auth = authenticationManager.authenticate(token);

        return auth;
    }

    @Override
    protected  void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication result) throws ServletException, IOException {
//        super.successfulAuthentication(request, response, chain, result);
        UserPrincipal principal =  (UserPrincipal) result.getPrincipal();
        String authToken = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.expirationTime))
                .sign(Algorithm.HMAC256(JwtProperties.SECRET.getBytes()));

        response.addHeader(JwtProperties.headerString, JwtProperties.tokenPrefix + authToken);
    }

}
