package com.quotes.service.impl;

import com.quotes.domain.entity.Role;
import com.quotes.domain.entity.UserEntity;
import com.quotes.domain.request.RegisterRequest;
import com.quotes.repository.UserRepository;
import com.quotes.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void createUser(RegisterRequest req) {
        var user = UserEntity.builder()
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }
}