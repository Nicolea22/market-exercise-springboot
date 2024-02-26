package com.nico.market.service;

import com.nico.market.model.dto.request.SignInRequest;
import com.nico.market.model.dto.request.SignUpRequest;
import com.nico.market.model.dto.response.JwtAuthenticationResponse;
import org.springframework.web.client.HttpStatusCodeException;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
}
