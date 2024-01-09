package org.sapient.app.bankApp.service;

import lombok.RequiredArgsConstructor;
import org.sapient.app.bankApp.model.AuthenticationRequest;
import org.sapient.app.bankApp.model.AuthenticationResponse;
import org.sapient.app.bankApp.model.CustomerAuth;
import org.sapient.app.bankApp.model.RegisterRequest;
import org.sapient.app.bankApp.repository.CustomerAuthRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private final CustomerAuthRepository customerAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = CustomerAuth.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        customerAuthRepository.save(user);
        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = customerAuthRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
