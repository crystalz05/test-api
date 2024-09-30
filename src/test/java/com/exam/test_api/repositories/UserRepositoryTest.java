package com.exam.test_api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.exam.test_api.model.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

	
	@Autowired
    private UserRepository userRepository;
    
	private User user;
	
	@BeforeEach
	void setUp() {
        user = new User();
        user.setName("John Doe");
        user.setUsername("john_doe");
        user.setPassword("password123");
	}
	
	@Test
    void testSaveUser() {
		
        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("John Doe");
        assertThat(savedUser.getUsername()).isEqualTo("john_doe");
    }

    @Test
    void testFindByUsername() {
    	
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("john_doe");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("John Doe");
        assertThat(foundUser.get().getUsername()).isEqualTo("john_doe");
    }

    @Test
    void testUpdateUser() {
        User savedUser = userRepository.save(user);

        savedUser.setPassword("new_password123");
        User updatedUser = userRepository.save(savedUser);

        assertThat(updatedUser.getPassword()).isEqualTo("new_password123");
    }

    @Test
    void testDeleteUser() {
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        userRepository.deleteById(userId);

        Optional<User> deletedUser = userRepository.findById(userId);
        assertThat(deletedUser).isNotPresent();
    }
}
