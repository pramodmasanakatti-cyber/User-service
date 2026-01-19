package com.userservice.controller;

import com.userservice.config.JwtConfig;
import com.userservice.dto.request.LoginRequest;
import com.userservice.dto.response.JwtResponse;
import com.userservice.entity.User;
import com.userservice.exception.UserNotFoundException;
import com.userservice.repository.UserRepository;
import com.userservice.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
      try{
          authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         loginRequest.getEmail(),
                         loginRequest.getPassword()
                 )
        );
      } catch (AuthenticationException exception) {
          throw exception;
      }

        User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UserNotFoundException("User not found for the email: " + loginRequest.getEmail()));

        String token=jwtService.generateToken(user);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
