package com.exam.test_api.controllers;



import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.exam.test_api.model.Question;
import com.exam.test_api.services.QuestionService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuestionsController.class)
class QuestionsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuestionService questionService;

	private Question question1;
	private Question question2;
	private List<Question> questions;

	@BeforeEach
	void setUp() {
		question1 = new Question();
		question1.setId(1L);
		question1.setType("anime");
		question1.setText("In Tokyo Ghoul, what kind of creature is Kaneki transformed into?");
		question1.setOptions(Arrays.asList("Werewolf", "Vampire", "Ghoul", "Zombie"));
		question1.setCorrectAnswer("Ghoul");

		question2 = new Question();
		question2.setId(2L);
		question2.setType("anime");
		question2.setText("Who is the main protagonist in Attack on Titan?");
		question2.setOptions(Arrays.asList("Armin Arlert", "Mikasa Ackerman", "Levi Ackerman", "Eren Yeager"));
		question2.setCorrectAnswer("Eren Yeager");

		questions = IntStream.range(0, 25)
				.mapToObj(i -> new Question((long) i, "Question " + i, "anime",
						Arrays.asList("Option1", "Option2", "Option3", "Option4"), "Option1"))
				.collect(Collectors.toList());

	}

	@Test
	void testRetriveQuestionById() throws Exception {
	    
	    when(questionService.retrieveQuestionById(anyLong())).thenReturn(Optional.of(question1));
	    
	    mockMvc.perform(MockMvcRequestBuilders.get("/api/questions/id/1"))
	        .andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("anime"))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("In Tokyo Ghoul, what kind of creature is Kaneki transformed into?"))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.options.length()").value(4))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.correctAnswer").value("Ghoul"))
	        .andReturn();
	    
	    verify(questionService, times(1)).retrieveQuestionById(anyLong());
	}

	@Test
	void testRetrieveQuestionById_NotFound() throws Exception {
		
		when(questionService.retrieveQuestionById(anyLong())).thenReturn(Optional.empty());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/questions/id/1"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	    verify(questionService, times(1)).retrieveQuestionById(anyLong());
	}

	@Test
	void testRetrieveQuestionByType() throws Exception {
		
        when(questionService.retrieveQuestionsByType(anyString())).thenReturn(Optional.of(questions.stream()
                .filter(q -> q.getType().equalsIgnoreCase("anime"))
                .limit(20)
                .collect(Collectors.toList())));
        
		mockMvc.perform(MockMvcRequestBuilders.get("/api/questions/anime"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(20))
		.andReturn();
		
	    verify(questionService, times(1)).retrieveQuestionsByType(anyString());
	}
	
	@Test
	void testRetrieveAllQuestions() throws Exception {
		
		when(questionService.retrieveAllQuestions()).thenReturn(Optional.of(questions));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/questions"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(25))
		.andReturn();
		
		verify(questionService, times(1)).retrieveAllQuestions();
		
	}

}
