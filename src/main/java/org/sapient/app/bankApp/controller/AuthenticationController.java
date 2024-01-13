package org.sapient.app.bankApp.controller;

import lombok.RequiredArgsConstructor;
import org.sapient.app.bankApp.model.AuthenticationRequest;
import org.sapient.app.bankApp.model.AuthenticationResponse;
import org.sapient.app.bankApp.model.RegisterRequest;
import org.sapient.app.bankApp.service.AuthenticationServiceImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        AuthenticationResponse res = authenticationService.register(request);
        if(res == null){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
         return ResponseEntity.ok(res);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    )
    {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
