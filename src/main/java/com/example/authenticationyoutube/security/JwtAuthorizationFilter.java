package com.example.authenticationyoutube.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authenticationyoutube.UserPrincipal;
import com.example.authenticationyoutube.model.User;
import com.example.authenticationyoutube.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository= userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain);
        String header = request.getHeader(JwtProperties.headerString);

        //if heder is null or doesn't contain token, delegate to Spring impl and return;
        if(header == null || !header.startsWith(JwtProperties.tokenPrefix)){
            chain.doFilter(request, response);
            return;
        }

        //if header is present, try and grab user principal from DB and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //continue with the chain filter
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {

        String token = request.getHeader(JwtProperties.headerString);
        if(token != null){
            //parse the token and validate it
            String username = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .verify(token.replace(JwtProperties.tokenPrefix, ""))
                    .getSubject();

            //search the db if we find the user with the token subject ( username)
            //if so grab user details and create spring auth token using username, passwor dand authorities/role
            if(username != null ){
                User user = userRepository.findByUsername(username);
                UserPrincipal userPrincipal = new UserPrincipal(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, userPrincipal.getAuthorities());

                return auth;
            }
            return null;
        }
        return null;
    }

}
