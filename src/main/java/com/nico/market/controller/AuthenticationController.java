package com.nico.market.controller;

import com.nico.market.exception.CustomerNotFoundException;
import com.nico.market.model.dto.request.SignInRequest;
import com.nico.market.model.dto.request.SignUpRequest;
import com.nico.market.model.dto.response.JwtAuthenticationResponse;
import com.nico.market.service.AuthenticationService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @ApiResponse
    public JwtAuthenticationResponse signup(@RequestBody SignUpRequest request)  {
        return authenticationService.signup(request);
    }

    @PostMapping("/signin")
    @ApiResponse
    public JwtAuthenticationResponse signin(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }

}
