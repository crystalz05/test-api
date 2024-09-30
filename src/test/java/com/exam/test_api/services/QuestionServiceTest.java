package com.exam.test_api.services;

import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.exam.test_api.model.Question;
import com.exam.test_api.repositories.QuestionsRepository;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionsRepository questionsRepository;

    @InjectMocks
    private QuestionService questionService;

    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question();
        question.setId(1L);
        question.setText("What is Java?");
        question.setType("Multiple Choice");
        question.setOptions(Arrays.asList("Programming Language", "Coffee", "Animal", "Car"));
        question.setCorrectAnswer("Programming Language");
        
        // Initialize QuestionService with QuestionsRepository
        questionService = new QuestionService(questionsRepository);
    }

    @Test
    void testCreateQuestion() {
        when(questionsRepository.save(any(Question.class))).thenReturn(question);

        Question createdQuestion = questionService.createQuestion(question);

        assertThat(createdQuestion).isNotNull();
        assertThat(createdQuestion.getText()).isEqualTo("What is Java?");
        verify(questionsRepository, times(1)).save(question);
    }

    @Test
    void testDeleteQuestion() {
        doNothing().when(questionsRepository).deleteById(anyLong());

        questionService.deleteQuestion(1L);

        verify(questionsRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRetrieveQuestionById() {
        when(questionsRepository.findById(anyLong())).thenReturn(Optional.of(question));

        Optional<Question> retrievedQuestion = questionService.retrieveQuestionById(1L);

        assertThat(retrievedQuestion).isPresent();
        assertThat(retrievedQuestion.get().getText()).isEqualTo("What is Java?");
    }

    @Test
    void testRetrieveQuestionsByType() {
        List<Question> questions = Arrays.asList(question, question);
        when(questionsRepository.findAll()).thenReturn(questions);

        Optional<List<Question>> retrievedQuestions = questionService.retrieveQuestionsByType("Multiple Choice");

        assertThat(retrievedQuestions).isPresent();
        assertThat(retrievedQuestions.get()).hasSizeLessThanOrEqualTo(20);
        verify(questionsRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveAllQuestions() {
        List<Question> questions = Arrays.asList(question, new Question());
        when(questionsRepository.findAll()).thenReturn(questions);

        Optional<List<Question>> retrievedQuestions = questionService.retrieveAllQuestions();

        assertThat(retrievedQuestions).isPresent();
        assertThat(retrievedQuestions.get()).hasSize(2);
        verify(questionsRepository, times(1)).findAll();
    }
}
