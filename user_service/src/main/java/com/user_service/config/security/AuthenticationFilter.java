package com.user_service.config.security;

import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//The AuthenticationFilter class is a custom filter used in Spring Security to handle authentication logic for incoming requests.
@Log
public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("request"+ request + " filterChain"+ filterChain.toString());
        try {
            // Attempt to retrieve authentication information based on the incoming request
            Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest) request);
            // Set the retrieved authentication object in the SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exp) {
            // If an exception occurs (e.g., authentication failure), handle it
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            // Set the HTTP status code to 401 Unauthorized
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // Set the content type of the response to JSON
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            // Create a PrintWriter to write the exception message to the response
            PrintWriter writer = httpResponse.getWriter();
            // Write the exception message to the response
            writer.print(exp.getMessage());

            // Flush the writer and close it
            writer.flush();
            writer.close();
        }

        // Continue the filter chain execution
        filterChain.doFilter(request, response);
    }
}
