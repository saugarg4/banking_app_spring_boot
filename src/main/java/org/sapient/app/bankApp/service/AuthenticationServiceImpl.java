package org.sapient.app.bankApp.service;

import lombok.RequiredArgsConstructor;
import org.sapient.app.bankApp.model.*;
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
    public AuthenticationResponse register(RegisterRequest request){
        var user = customerAuthRepository.findByEmail(request.getEmail())
                .orElse(null);
        if(user == null){
            user = CustomerAuth.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            customerAuthRepository.save(user);
            var jwtToken= jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        return null;


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
        Long accountNumber = (user.getAccount() == null) ? 0 : user.getAccount().getAccountNumber();
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .accountNumber(accountNumber)
                .build();
    }

    public Account findAccountByEmail(String email){
        var user = customerAuthRepository.findByEmail(email).orElse(null);
        return user != null ? user.getAccount() : null;
    }

    public void updateUserAuthAccount(Account account){
        var user = customerAuthRepository.findByEmail(account.getCustomer().getCustomerEmail()).orElseThrow();
        user.setAccount(account);
        customerAuthRepository.save(user);

    }
}
