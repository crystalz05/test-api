package com.exam.test_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.test_api.exceptions.ResourceNotFoundException;
import com.exam.test_api.model.Answer;
import com.exam.test_api.model.Question;
import com.exam.test_api.model.Submission;
import com.exam.test_api.model.User;
import com.exam.test_api.repositories.ExamRepository;
import com.exam.test_api.repositories.SubmissionRepository;
import com.exam.test_api.repositories.UserRepository;

@Service
public class SubmissionService {

    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final SubmissionRepository submissionRepository;

    public SubmissionService(UserRepository userRepository, ExamRepository examRepository,
                             SubmissionRepository submissionRepository) {
        this.userRepository = userRepository;
        this.examRepository = examRepository;
        this.submissionRepository = submissionRepository;
    }

    @Transactional
    public Long createSubmission(Long userId, Long examId, List<Answer> answers) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + examId));
        
        Long score = calculateScore(answers, examId);

        Submission submission = new Submission();
        submission.setUser(user);
        submission.setExamId(examId);
        submission.setScore(score);
        submissionRepository.save(submission);

        return score;
    }

    private Long calculateScore(List<Answer> answers, Long examId) {
        List<Question> questions = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + examId))
                .getQuestions();

        long score = 0;

        for (Question question : questions) {
            for (Answer answer : answers) {
                if (answer.getId() != null && 
                    answer.getId().equals(question.getId()) && 
                    answer.getAnswer() != null && 
                    answer.getAnswer().equalsIgnoreCase(question.getCorrectAnswer())) {
                    score++;
                }
            }
        }

        return score;
    }
}
