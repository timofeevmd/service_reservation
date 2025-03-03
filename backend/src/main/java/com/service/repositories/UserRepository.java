package com.service.repositories;

import com.service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     *
     * @param username the username of the user
     * @return Optional containing the found user, or an empty Optional if the user is not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with the given username exists.
     *
     * @param username the username of the user
     * @return true if the user exists, otherwise false
     */
    boolean existsByUsername(String username);
}
