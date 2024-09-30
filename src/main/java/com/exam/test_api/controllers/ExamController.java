package com.exam.test_api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.test_api.model.Exam;
import com.exam.test_api.repositories.ExamRepository;
import com.exam.test_api.services.ExamService;

@RestController
@RequestMapping("/exam")
public class ExamController {

	private ExamService examService;

	public ExamController(ExamService examService) {
		super();
		this.examService = examService;
	}

	@PostMapping("/create")
	public ResponseEntity<Long> createExam(@RequestBody Exam exam) {
		
		Long examId = examService.createExam(exam);

		
		if (examId > 0) {
			return new ResponseEntity<>(examId, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Optional<Exam>> retrieveExamById(@PathVariable Long id) {
		Optional<Exam> exam = examService.retrieveExamById(id);

		if (exam.isPresent()) {
			return new ResponseEntity<>(exam, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {

		if (examService.deleteExam(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/all-exams")
	public ResponseEntity<List<Exam>> retriveAllExams(){
		return new ResponseEntity<>(examService.retriveAllExams(), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-all")
	public ResponseEntity<Void> deleteAllExams(){
		examService.deleteAllExams();
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
