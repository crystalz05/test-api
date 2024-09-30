package com.exam.test_api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.exam.test_api.model.Question;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class QuestionsRepositoryTest {

    @Autowired
    private QuestionsRepository questionsRepository;

    @Test
    void testSaveQuestion() {
        Question question = new Question();
        question.setText("What is Java?");
        question.setType("Multiple Choice");
        question.setOptions(Arrays.asList("A programming language", "A coffee brand", "A country", "A car brand"));
        question.setCorrectAnswer("A programming language");

        Question savedQuestion = questionsRepository.save(question);

        assertThat(savedQuestion).isNotNull();
        assertThat(savedQuestion.getId()).isNotNull();
        assertThat(savedQuestion.getText()).isEqualTo("What is Java?");
    }

    @Test
    void testFindByType() {
        Question question1 = new Question();
        question1.setText("What is Java?");
        question1.setType("Multiple Choice");
        question1.setOptions(Arrays.asList("A programming language", "A coffee brand", "A country", "A car brand"));
        question1.setCorrectAnswer("A programming language");

        Question question2 = new Question();
        question2.setText("What is Python?");
        question2.setType("Multiple Choice");
        question2.setOptions(Arrays.asList("A programming language", "A snake", "A country", "A framework"));
        question2.setCorrectAnswer("A programming language");

        questionsRepository.save(question1);
        questionsRepository.save(question2);

        Pageable pageable = PageRequest.of(0, 10);
        List<Question> questions = questionsRepository.findByType("Multiple Choice", pageable);

        assertThat(questions).hasSize(2);
        assertThat(questions.get(0).getText()).isEqualTo("What is Java?");
        assertThat(questions.get(1).getText()).isEqualTo("What is Python?");
    }

    @Test
    void testPagination() {
        for (int i = 1; i <= 15; i++) {
            Question question = new Question();
            question.setText("Question " + i);
            question.setType("Multiple Choice");
            question.setOptions(Arrays.asList("Option 1", "Option 2", "Option 3", "Option 4"));
            question.setCorrectAnswer("Option 1");
            questionsRepository.save(question);
        }

        Pageable firstPage = PageRequest.of(0, 10);
        List<Question> firstPageQuestions = questionsRepository.findByType("Multiple Choice", firstPage);
        assertThat(firstPageQuestions).hasSize(10);

        Pageable secondPage = PageRequest.of(1, 10);
        List<Question> secondPageQuestions = questionsRepository.findByType("Multiple Choice", secondPage);
        assertThat(secondPageQuestions).hasSize(5);
    }
}
