package com.service.security;

import com.service.models.User;
import com.service.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor with dependency injection for UserRepository.
     *
     * @param userRepository repository for user operations
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user details by username.
     *
     * @param username the username
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Searching for user in the database: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("❌ User not found: " + username));

        System.out.println("User found: " + user.getUsername());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("USER"))
        );
    }
}
