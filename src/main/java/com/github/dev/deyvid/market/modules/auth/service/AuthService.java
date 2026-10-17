package com.github.dev.deyvid.market.modules.auth.service;

import com.github.dev.deyvid.market.infrastructure.exceptions.EmailAlreadyExistsException;
import com.github.dev.deyvid.market.infrastructure.security.JwtTokenProvider;
import com.github.dev.deyvid.market.modules.auth.model.User;
import com.github.dev.deyvid.market.modules.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider tokenProvider,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public User register(String email, String rawPassword, String name) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("E-mail já cadastrado no sistema.");
        }

        String encryptedPassword = passwordEncoder.encode(rawPassword);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(encryptedPassword);
        newUser.setName(name);

        return userRepository.save(newUser);
    }

    public String login(String email, String rawPassword) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(email, rawPassword);
        var auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();

        return tokenProvider.generateToken(user);
    }
}