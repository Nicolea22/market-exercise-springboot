package com.nico.market.service.impl;

import com.nico.market.exception.CustomerNotFoundException;
import com.nico.market.model.dto.request.SignInRequest;
import com.nico.market.model.dto.request.SignUpRequest;
import com.nico.market.model.dto.response.JwtAuthenticationResponse;
import com.nico.market.model.entity.Role;
import com.nico.market.model.entity.User;
import com.nico.market.repository.UserRepository;
import com.nico.market.service.AuthenticationService;
import com.nico.market.service.JwtService;
import com.nico.market.service.UserService;
import com.nico.market.validator.ValidationResult;
import com.nico.market.validator.signin.SignInValidation;
import com.nico.market.validator.signup.SignUpValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService  {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SignUpValidation signUpValidation;
    private final SignInValidation signInValidation;

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        ValidationResult result = signUpValidation.validate(request);
        if(result.notValid()) {
            throw new UsernameNotFoundException("Email already used.");
        }
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signin(SignInRequest request) throws HttpStatusCodeException{
        ValidationResult validate = signInValidation.validate(request);
        if (validate.notValid()) {
            throw new UsernameNotFoundException("User or password are incorrect.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail());
        var jwt = jwtService.generateToken(user.get());
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

}