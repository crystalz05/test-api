package com.exam.test_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.test_api.model.Answer;
import com.exam.test_api.services.SubmissionService;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

	private SubmissionService submissionService;

	public SubmissionController(SubmissionService submissionService) {
		super();
		this.submissionService = submissionService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createSubmission(@RequestBody SubmissionRequest submissionRequest) {
		try {
			Long score = submissionService.createSubmission(submissionRequest.getUserId(), submissionRequest.getExamId(),
					submissionRequest.getAnswers());
			
			System.out.println("from controller" +submissionRequest.getAnswers());


            return new ResponseEntity<>(score, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Resource not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An internal error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	public static class SubmissionRequest {
		private Long userId;
		private Long examId;
		private List<Answer> answers;

		// Getters and setters
		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getExamId() {
			return examId;
		}

		public void setExamId(Long examId) {
			this.examId = examId;
		}

		public List<Answer> getAnswers() {
			return answers;
		}

		public void setAnswers(List<Answer> answers) {
			this.answers = answers;
		}
	}
}