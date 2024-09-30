package com.exam.test_api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exam.test_api.model.User;
import com.exam.test_api.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setUsername("testuser");
        user.setPassword("password");
        
        // Initialize UserService with UserRepository
        userService = new UserService(userRepository);
    }

    @Test
    void testRegisterUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.registeUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        boolean isDeleted = userService.deleteUserById(1L);

        verify(userRepository, times(1)).deleteById(1L);
        assertThat(isDeleted).isTrue();
    }

    @Test
    void testRetrieveUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> retrievedUser = userService.retrieveUserById(1L);

        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void testRetrieveAllUsers() {
        List<User> users = Arrays.asList(user, new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> retrievedUsers = userService.retrieveAllUsers();

        assertThat(retrievedUsers).hasSize(2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean isCreated = userService.createUser(user);

        verify(userRepository, times(1)).save(user);
        assertThat(isCreated).isTrue();
    }
}
