package com.user_service.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

// Define a custom authentication token class named ApiKeyAuthentication
public class ApiKeyAuthentication extends AbstractAuthenticationToken {
    // Define a field to store the API key
    private final String apiKey;

    // Constructor to initialize the API key and authorities
    public ApiKeyAuthentication(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        // Call the superclass constructor and pass the authorities
        super(authorities);
        // Initialize the API key field
        this.apiKey = apiKey;
        // Mark the authentication token as authenticated
        setAuthenticated(true);
    }

    // Implementation of the getCredentials method
    @Override
    public Object getCredentials() {
        // In this implementation, return null since there are no credentials associated with API key authentication
        return null;
    }

    // Implementation of the getPrincipal method
    @Override
    public Object getPrincipal() {
        // Return the API key as the principal
        return apiKey;
    }
}
