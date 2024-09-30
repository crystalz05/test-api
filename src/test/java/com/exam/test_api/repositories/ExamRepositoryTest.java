package com.exam.test_api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.exam.test_api.model.Exam;
import com.exam.test_api.model.Question;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ExamRepositoryTest {

	@Autowired
    private ExamRepository examRepository;
      
    private Exam exam;
    
    @BeforeEach
    void setUp() {
    	
        exam = new Exam();
        exam.setId(1L);
        exam.setExamType("programming");

        Question question1 = new Question();
        question1.setText("What is Java?");
        question1.setType("Multiple Choice");
        question1.setOptions(Arrays.asList("A programming language", "A coffee brand", "A country", "A car brand"));
        question1.setCorrectAnswer("A programming language");

        Question question2 = new Question();
        question2.setText("Which of the following is not an OOP concept?");
        question2.setType("Multiple Choice");
        question2.setOptions(Arrays.asList("Abstraction", "Encapsulation", "Inheritance", "Compilation"));
        question2.setCorrectAnswer("Compilation");
        
        exam.setQuestions(Arrays.asList(question1, question2));

    }

    @Test
    void testSaveExam() {
        Exam savedExam = examRepository.save(exam);

        assertThat(savedExam).isNotNull();
        assertThat(savedExam.getId()).isNotNull();
        assertThat(savedExam.getExamType()).isEqualTo("programming");
        assertThat(savedExam.getQuestions()).hasSize(2);
        assertThat(savedExam.getQuestions().get(0).getText()).isEqualTo("What is Java?");
    }

    @Test
    void testFindById() {
    	
        Exam savedExam = examRepository.save(exam);

        Optional<Exam> foundExam = examRepository.findById(savedExam.getId());

        assertThat(foundExam).isPresent();
        assertThat(foundExam.get().getExamType()).isEqualTo("programming");
        assertThat(foundExam.get().getQuestions()).hasSize(2);
        assertThat(foundExam.get().getQuestions().get(0).getText()).isEqualTo("What is Java?");
    }

    @Test
    void testUpdateExam() {

        Exam savedExam = examRepository.save(exam);
        
        savedExam.setExamType("Updated Quiz");
        
        Exam updatedExam = examRepository.save(savedExam);

        assertThat(updatedExam).isNotNull();
        assertThat(updatedExam.getId()).isEqualTo(savedExam.getId());
        assertThat(updatedExam.getExamType()).isEqualTo("Updated Quiz");
        assertThat(updatedExam.getQuestions()).hasSize(2);  // Assuming the setup adds 2 questions
        assertThat(updatedExam.getQuestions().get(0).getText()).isEqualTo("What is Java?");
    }

    @Test
    void testDeleteExam() {

        Exam savedExam = examRepository.save(exam);
        Long examId = savedExam.getId();

        examRepository.deleteById(examId);

        Optional<Exam> deletedExam = examRepository.findById(examId);
        assertThat(deletedExam).isNotPresent();
    }
}
