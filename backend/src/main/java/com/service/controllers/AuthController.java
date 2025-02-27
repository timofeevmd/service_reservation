package com.service.controllers;

import com.service.DTO.LoginRequest;
import com.service.DTO.RegisterRequest;
import com.service.models.User;
import com.service.security.JwtTokenProvider;
import com.service.services.UserService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    private final Counter successfulLogins;
    private final Counter failedLogins;
    private final Counter userRegistrations;
    private final Counter userRegistrationFailure;
    private final Timer authRequestTimer;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, MeterRegistry meterRegistry) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;

        this.successfulLogins = meterRegistry.counter("auth.success.count");
        this.failedLogins = meterRegistry.counter("auth.failed.count");
        this.userRegistrations = meterRegistry.counter("auth.register.count");
        this.userRegistrationFailure = meterRegistry.counter("auth.register.count.failure");
        this.authRequestTimer = meterRegistry.timer("auth.request.time");
    }

    /**
     * Authenticate the user and issue a JWT token.
     *
     * @param loginRequest request containing username and password
     * @return JWT token upon successful authentication
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Raw request body: " + loginRequest);
        logger.info("Attempting login for user: " + loginRequest.getUsername());

        return authRequestTimer.record(() -> {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );

                String token = jwtTokenProvider.generateToken(authentication);

                Map<String, String> response = new HashMap<>();
                response.put("token", token);

                logger.info("Login successful for user: " + loginRequest.getUsername());
                successfulLogins.increment();
                return ResponseEntity.ok(response);
            } catch (AuthenticationException e) {
                logger.warning("Login failed for user: " + loginRequest.getUsername() + " - " + e.getMessage());

                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid username or password");
                failedLogins.increment();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
        });

    }

    /**
     * Endpoint for user registration.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return authRequestTimer.record(() -> {
            try {
                if (userService.existsByUsername(registerRequest.getUsername())) {
                    userRegistrationFailure.increment();
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this username already exists");
                }

                SecurityContextHolder.clearContext();

                User newUser = new User();
                newUser.setUsername(registerRequest.getUsername());
                newUser.setPassword(registerRequest.getPassword());
                newUser.setRole("USER");

                userService.saveUser(newUser);

                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                        newUser.getUsername(), newUser.getPassword(), List.of(new SimpleGrantedAuthority("USER"))
                );

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                String token = jwtTokenProvider.generateToken(authentication);

                Map<String, String> response = new HashMap<>();
                response.put("message", "user was register");
                response.put("token", token);
                userRegistrations.increment();
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } catch (Exception e) {
                userRegistrationFailure.increment();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration error: " + e.getMessage());
            }
        });

    }

    /**
     * Endpoint for logging out (does not revoke token, only demonstrational purpose).
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Вы успешно вышли");
    }
}