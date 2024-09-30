package com.exam.test_api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.exam.test_api.model.Exam;
import com.exam.test_api.services.ExamService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExamController.class)
class ExamControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ExamService examService;

	private Exam exam;

	@BeforeEach
	void setUp() {

		exam = new Exam();
		exam.setId(1L);
		exam.setExamType("anime");
	}

	@Test
	void testCreateExam_Success() throws Exception {
		
		when(examService.createExam(any(Exam.class))).thenReturn(anyLong());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/exam/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(exam)))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		verify(examService, times(1)).createExam(any(Exam.class));
	}

	@Test
	void testCreateExam_NotFound() throws Exception {
		
		when(examService.createExam(any(Exam.class))).thenReturn(-1L);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/exam/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(exam)))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		
		verify(examService, times(1)).createExam(any(Exam.class));
	}

	@Test
	void testRetrieveExamById() throws Exception {
		when(examService.retrieveExamById(anyLong())).thenReturn(Optional.of(exam));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/exam/id/1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
		.andExpect(MockMvcResultMatchers.jsonPath("$.examType").value("anime"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		verify(examService, times(1)).retrieveExamById(anyLong());
	}

	@Test
	void testDeleteById() throws Exception{
		when(examService.deleteExam(anyLong())).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/exam/delete/1"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		verify(examService, times(1)).deleteExam(anyLong());
	}

	@Test
	void testDeleteById_NotFound() throws Exception{
		when(examService.deleteExam(anyLong())).thenReturn(false);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/exam/delete/1"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		verify(examService, times(1)).deleteExam(anyLong());
	}

}
