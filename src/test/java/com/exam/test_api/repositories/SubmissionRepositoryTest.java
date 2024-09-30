package com.exam.test_api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.exam.test_api.model.Submission;
import com.exam.test_api.model.User;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class SubmissionRepositoryTest {

	@Autowired
    private SubmissionRepository submissionRepository;
	
	private User user;
	private Submission submission;
	
	@BeforeEach
	void setUp() {
        user = new User();
        user.setName("John Doe");
        user.setUsername("john_doe");
        user.setPassword("password123");
        
        submission = new Submission();
        submission.setUser(user);
        submission.setExamId(1L);
        submission.setScore(90L);
        
	}
	
	@Test
    void testSaveSubmission() {

        Submission savedSubmission = submissionRepository.save(submission);

        assertThat(savedSubmission).isNotNull();
        assertThat(savedSubmission.getId()).isNotNull();
        assertThat(savedSubmission.getUser().getUsername()).isEqualTo("john_doe");
        assertThat(savedSubmission.getExamId()).isEqualTo(1L);
        assertThat(savedSubmission.getScore()).isEqualTo(90L);
    }

    @Test
    void testFindById() { 
        Submission savedSubmission = submissionRepository.save(submission);

        Optional<Submission> foundSubmission = submissionRepository.findById(savedSubmission.getId());

        assertThat(foundSubmission).isPresent();
        assertThat(foundSubmission.get().getUser().getUsername()).isEqualTo("john_doe");
        assertThat(foundSubmission.get().getExamId()).isEqualTo(1L);
        assertThat(foundSubmission.get().getScore()).isEqualTo(90L);
    }

    @Test
    void testUpdateSubmission() {
        Submission savedSubmission = submissionRepository.save(submission);

        savedSubmission.setScore(95L);
        Submission updatedSubmission = submissionRepository.save(savedSubmission);

        assertThat(updatedSubmission.getScore()).isEqualTo(95L);
    }

    @Test
    void testDeleteSubmission() {
    	
        Submission savedSubmission = submissionRepository.save(submission);
        Long submissionId = savedSubmission.getId();

        submissionRepository.deleteById(submissionId);

        Optional<Submission> deletedSubmission = submissionRepository.findById(submissionId);
        assertThat(deletedSubmission).isNotPresent();
    }
}
