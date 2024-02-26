package com.nico.market.service;

import com.nico.market.model.dto.request.SignUpRequest;
import com.nico.market.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    User save(User request);
}
