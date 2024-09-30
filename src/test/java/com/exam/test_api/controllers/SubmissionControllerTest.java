package com.exam.test_api.controllers;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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

import com.exam.test_api.model.Answer;
import com.exam.test_api.services.SubmissionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SubmissionController.class)
class SubmissionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SubmissionService submissionService;

	private SubmissionController.SubmissionRequest submissionRequest;

	@Test
	@BeforeEach
	void setUp() {

		List<Answer> answers = Arrays.asList(new Answer(1L, "Answer1"), new Answer(2L, "Answer2"),
				new Answer(3L, "Answer3"));

		submissionRequest = new SubmissionController.SubmissionRequest();
		submissionRequest.setUserId(1L);
		submissionRequest.setExamId(1L);
		submissionRequest.setAnswers(answers);

	}

	@Test
    void testCreateSubmission_Success() throws Exception {
    	when(submissionService.createSubmission(anyLong(), anyLong(), anyList())).thenReturn(3L);
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/submissions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(submissionRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(submissionService, times(1)).createSubmission(anyLong(), anyLong(), anyList());
    }

	@Test
	void testCreateSubmission_NotFound() throws Exception {
		
//		when(submissionService.createSubmission(anyLong(), anyLong(), anyList())).thenReturn(null);
        doThrow(new RuntimeException("Not Found")).when(submissionService).createSubmission(anyLong(), anyLong(), anyList());

		mockMvc.perform(MockMvcRequestBuilders.post("/submissions/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(submissionRequest)))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
        verify(submissionService, times(1)).createSubmission(anyLong(), anyLong(), anyList());
	}

}
