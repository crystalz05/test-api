package com.exam.test_api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exam.test_api.model.Exam;
import com.exam.test_api.model.Question;
import com.exam.test_api.repositories.ExamRepository;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExamService examService;

    private Exam exam;
    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question();
        question.setId(1L);
        question.setText("What is Java?");
        question.setType("Multiple Choice");
        question.setOptions(Arrays.asList("Programming Language", "Coffee", "Animal", "Car"));
        question.setCorrectAnswer("Programming Language");

        exam = new Exam();
        exam.setId(1L);
        exam.setExamType("Multiple Choice");
        exam.setQuestions(Arrays.asList(question));

        // Initialize ExamService with ExamRepository and QuestionService
        examService = new ExamService(examRepository, questionService);
    }

    @Test
    void testCreateExam() {
        when(questionService.retrieveQuestionsByType(anyString())).thenReturn(Optional.of(Arrays.asList(question)));
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        Long isCreated = examService.createExam(exam);

        assertThat(isCreated).isEqualTo(anyLong()>0);
        verify(examRepository, times(1)).save(exam);
    }

    @Test
    void testDeleteExam() {
        when(examRepository.existsById(anyLong())).thenReturn(true);
        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));

        boolean isDeleted = examService.deleteExam(1L);

        assertThat(isDeleted).isTrue();
        verify(examRepository, times(1)).findById(1L);
        verify(examRepository, times(1)).save(exam);
        verify(examRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRetrieveExamById() {
        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));

        Optional<Exam> retrievedExam = examService.retrieveExamById(1L);

        assertThat(retrievedExam).isPresent();
        assertThat(retrievedExam.get().getExamType()).isEqualTo("Multiple Choice");
    }
}
