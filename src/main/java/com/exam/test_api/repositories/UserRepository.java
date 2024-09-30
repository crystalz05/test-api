package com.exam.test_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.test_api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsernameIgnoreCase(String username);
    Optional<User> findByUsernameIgnoreCase(String username);

    // Remove or fix this method
    // User findByUserName(String username); // This will cause an error because 'userName' is not a property in the User entity.
}
