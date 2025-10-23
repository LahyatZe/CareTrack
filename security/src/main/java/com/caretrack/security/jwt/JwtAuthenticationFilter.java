package com.caretrack.security.jwt;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

/**
 * Filter responsible for authenticating a user based on the credentials provided in the
 * request body. When authentication is successful a fresh token pair is generated and sent
 * back to the client as JSON.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenService tokenService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtTokenService tokenService,
                                   ObjectMapper objectMapper) {
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        LoginRequest loginRequest = readLoginRequest(request);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        UserDetails principal = (UserDetails) authResult.getPrincipal();
        TokenPair tokenPair = tokenService.generateTokenPair(principal);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), TokenResponse.from(tokenPair));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), new ErrorResponse("Invalid credentials"));
    }

    private LoginRequest readLoginRequest(HttpServletRequest request) {
        try {
            byte[] body = StreamUtils.copyToByteArray(request.getInputStream());
            if (body.length == 0) {
                throw new AuthenticationServiceException("Empty authentication request body");
            }
            return objectMapper.readValue(body, LoginRequest.class);
        } catch (IOException ex) {
            throw new AuthenticationServiceException("Unable to read authentication request", ex);
        }
    }

    private record LoginRequest(String username, String password) {
    }

    private record ErrorResponse(String message) {
    }
}
