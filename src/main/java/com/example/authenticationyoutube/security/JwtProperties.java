package com.example.authenticationyoutube.security;

public class JwtProperties {

    public static final String SECRET = "thisIstheSecret";
    public static final String tokenPrefix = "Bearer";
    public static final String headerString = "Authorization";
    public static final int expirationTime = 864000000;
}
