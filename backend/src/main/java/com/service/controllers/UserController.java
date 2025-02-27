package com.service.controllers;

import com.service.models.User;
import com.service.security.JwtTokenProvider;
import com.service.services.UserService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final Counter userCreationRequests;
    private final Counter userCreationRequestsFailure;
    private final Counter userFetchRequests;
    private final Counter userFetchRequestsFailure;
    private final Timer userCreationRequestTimer;
    private final Timer userFetchRequestTimer;

    @Autowired
    public UserController(JwtTokenProvider jwtTokenProvider, UserService userService, MeterRegistry meterRegistry) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userCreationRequests = meterRegistry.counter("users.created.count");
        this.userCreationRequestsFailure = meterRegistry.counter("users.created.count.failure");
        this.userFetchRequests = meterRegistry.counter("users.fetched.count");
        this.userFetchRequestsFailure = meterRegistry.counter("users.fetched.count.failure");
        this.userCreationRequestTimer = meterRegistry.timer("users.creation.request.time");
        this.userFetchRequestTimer = meterRegistry.timer("users.fetch.request.time");
    }

    /**
     * Create a new user.
     *
     * @param user User object
     * @return created user
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userCreationRequestTimer.record(() -> {
            try {
                if (userService.existsByUsername(user.getUsername())) {
                    userCreationRequestsFailure.increment();
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with this username already exists");
                }
                User savedUser = userService.saveUser(user);
                userCreationRequests.increment();
                return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
            } catch (Exception e) {
                userCreationRequestsFailure.increment();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating user: " + e.getMessage());
            }
        });
    }


    /**
     * Retrieve a user by ID.
     *
     * @param id User ID
     * @return found user
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        return userFetchRequestTimer.record(() -> {
            System.out.println("TOKEN" + token);
            if (token == null || !token.startsWith("Bearer ")) {
                userFetchRequestsFailure.increment();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            try {
                String jwt = token.substring(7);
                String username = jwtTokenProvider.getUsernameFromToken(jwt);

                Optional<User> user = userService.findByUsername(username);
                userFetchRequests.increment();
                return user.map(ResponseEntity::ok).orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                );
            } catch (Exception e) {
                userFetchRequestsFailure.increment();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        });
    }


    /**
     * Retrieve all users.
     *
     * @return list of all users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll(); // Предполагается, что метод findAll() существует в UserService
        return ResponseEntity.ok(users);
    }

    /**
     * Delete a user by ID.
     *
     * @param id User ID
     * @return operation status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID \" + id + \" not found");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok("User with ID \" + id + \" successfully deleted");
    }
}
