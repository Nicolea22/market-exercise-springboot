package com.nico.market.service.impl;

import com.nico.market.model.dto.request.SignInRequest;
import com.nico.market.model.entity.User;
import com.nico.market.repository.UserRepository;
import com.nico.market.service.UserService;
import com.nico.market.validator.ValidationResult;
import com.nico.market.validator.signin.SignInValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) {
                SignInRequest sign = new SignInRequest();
                sign.setEmail(email);
                return userRepository.findByEmail(email).get();
            }
        };
    }

    public User save(User newUser) {
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }
        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

}
