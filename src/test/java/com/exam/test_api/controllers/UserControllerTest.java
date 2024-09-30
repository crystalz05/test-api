package com.exam.test_api.controllers;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.exam.test_api.model.User;
import com.exam.test_api.repositories.UserRepository;
import com.exam.test_api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;



@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;
	
    @MockBean
    private UserRepository userRepository;

	@BeforeEach
    void setUp() {
        reset(userRepository);
    }
	
	@Test
	void testRetriveUserById() throws Exception {

		User user = new User();
		user.setId(1L);
		user.setName("Paul Michael");
		user.setUsername("tyro");
		user.setPassword("dummy");

		when(userService.retrieveUserById(1L)).thenReturn(Optional.of(user));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Paul Michael"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tyro"));

		verify(userService, times(1)).retrieveUserById(1L);
	}

	@Test
	void testRetriveUserById_Notfound() throws Exception{
		
		when(userService.retrieveUserById(anyLong())).thenReturn(Optional.empty());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		verify(userService, times(1)).retrieveUserById(1L);
		
	}

	@Test
	void testRetriveAllUsers() throws Exception {

		User user1 = new User();
		user1.setId(1L);
		user1.setName("Paul Johnson");
		user1.setUsername("tyro");
		user1.setPassword("dummy");

		User user2 = new User();
		user2.setId(2L);
		user2.setName("Andrew Philip");
		user2.setUsername("Andrew");
		user2.setPassword("dummy");

		List<User> users = Arrays.asList(user1, user2);
		when(userService.retrieveAllUsers()).thenReturn(users);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/user")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Paul Johnson"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("tyro"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Andrew Philip"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("Andrew"));

		verify(userService, times(1)).retrieveAllUsers();
	}
	
	@Test
	void testCreateUser() throws Exception {

		User user = new User();
		user.setId(4L);
		user.setName("David John");
		user.setUsername("david");
		user.setPassword("1234");

		when(userService.createUser(any(User.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
		verify(userService, times(1)).createUser(any(User.class));
	}

    @Test
    void testCreateUser_Conflict() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setUsername("johndoe");
        user.setPassword("password");

        when(userService.createUser(any(User.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());

        verify(userService, times(1)).createUser(any(User.class));
    }
    
    @Test
    void testDeleteUserById() throws Exception {
    	
        when(userService.deleteUserById(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).deleteUserById(1L);
    }
    
    @Test
    void testDeleteUserById_NotFound() throws Exception {
        when(userService.deleteUserById(1L)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService, times(1)).deleteUserById(1L);
    }

}
