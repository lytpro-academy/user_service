package com.user_service.config.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

@Log
public class AuthenticationService {

    // Define the header name for the API key
    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    // Define the expected value of the API key
    private static final String HELLO_TOKEN = "helloToken";

    // Method to retrieve authentication based on the API key provided in the request
    public static Authentication getAuthentication(HttpServletRequest request) {
        // Extract the API key from the request header
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);

        log.info("apiKey-- "+apiKey);

        // Check if the API key is missing or does not match the expected value
        if (apiKey == null || !apiKey.equals(HELLO_TOKEN)) {
            // If the API key is invalid, throw a BadCredentialsException
            throw new BadCredentialsException("Invalid API Key");
        }
        // If the API key is valid, create and return an instance of ApiKeyAuthentication with no authorities
        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}
