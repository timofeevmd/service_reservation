package com.service.services;

import com.service.models.User;
import com.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return Optional containing the found user, or empty if the user is not found
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Checks if a user with the given username exists.
     *
     * @param username the username of the user
     * @return true if the user exists, false otherwise
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Saves a new user to the database.
     *
     * @param user the user object to save
     * @return the saved user
     */
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user
     * @return Optional containing the found user, or empty if the user is not found
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}