package com.exam.test_api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exam.test_api.model.Answer;
import com.exam.test_api.model.Exam;
import com.exam.test_api.model.Question;
import com.exam.test_api.model.Submission;
import com.exam.test_api.model.User;
import com.exam.test_api.repositories.ExamRepository;
import com.exam.test_api.repositories.SubmissionRepository;
import com.exam.test_api.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class SubmissionServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private ExamRepository examRepository;

	@Mock
	private SubmissionRepository submissionRepository;

	@InjectMocks
	private SubmissionService submissionService;

	private User user;
	private Exam exam;
	private Answer correctAnswer1;
	private Answer correctAnswer2;


	@BeforeEach
	@Test
	void setUp() {
		user = new User();
		user.setId(1L);
		user.setUsername("usertest");

		Question question1 = new Question();
		question1.setId(1L);
		question1.setType("Computer");
		question1.setText("What is Java");
		question1.setOptions(Arrays.asList("Programming Language", "Coffee", "Animal", "Car"));
		question1.setCorrectAnswer("Programming Language");

		Question question2 = new Question();
		question2.setId(2L);
		question2.setType("Computer");
		question2.setText("What is an object in Java?");
		question2.setOptions(Arrays.asList("A data type", "An instance of a class", "A method", "A variable"));
		question2.setCorrectAnswer("An instance of a class");

		exam = new Exam();
		exam.setId(1L);
		exam.setExamType("Computer");
		exam.setQuestions(Arrays.asList(question1, question2));
	    exam.setQuestions(Arrays.asList(question1, question2));


		correctAnswer1 = new Answer(1L, "Programming Language");
		correctAnswer2 = new Answer(2L, "An instance of a class");
	}

	@Test
	void testCreateSubmission() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));
		
		submissionService.createSubmission(1L, 1L, Arrays.asList(correctAnswer1, correctAnswer2 ));
        verify(submissionRepository, times(1)).save(any(Submission.class));
	}

	@Test
	void testCreateSubmission_ExamNotFound() {
	    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
	    when(examRepository.findById(anyLong())).thenReturn(Optional.empty()); // Simulate "exam not found"
	    	    	    
	    RuntimeException exception = assertThrows(RuntimeException.class, ()->{
	        submissionService.createSubmission(1L, 1L, Arrays.asList(correctAnswer1, correctAnswer2));
	    });
	    	    
	    assertThat(exception.getMessage()).isEqualTo("Exam not found");
	    verify(submissionRepository, times(0)).save(any(Submission.class));
	}
	
	@Test
	void testCreateSubmission_UserNotFound() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class, ()->{
			submissionService.createSubmission(1L,  1L, Arrays.asList(correctAnswer1, correctAnswer2));
		});
		
		assertThat(exception.getMessage()).isEqualTo("User not found");
		verify(submissionRepository, times(0)).save(any(Submission.class));
	}
	
	@Test
	void testCalculateScore() {
	    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));
		
        Long score = submissionService.createSubmission(1L, 1L, Arrays.asList(correctAnswer1, correctAnswer2));

        assertThat(score).isEqualTo(2L);
        
        verify(submissionRepository).save(argThat(submission -> 
        submission.getScore().equals(2L)));
	}

}
