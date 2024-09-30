package com.exam.test_api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.test_api.model.Question;
import com.exam.test_api.services.QuestionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {
	
    private final QuestionService questionService;
    
    public QuestionsController(QuestionService questionService) {
		super();
		this.questionService = questionService;
	}

	@GetMapping("/id/{id}")
    public ResponseEntity<Optional<Question>> retriveQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionService.retrieveQuestionById(id);
        
        if (question.isPresent()) {
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping("create-multiple-questions")
	public ResponseEntity<List<Question>> createMultipleQuestions(@RequestBody List<Question> questions){
		List<Question> savedQuestions = questionService.createMultipleQuestions(questions);
		return ResponseEntity.ok(savedQuestions);
	}
	
    @GetMapping("/{type}")
    public ResponseEntity<List<Question>> retrieveQuestionByType(@PathVariable String type) {
        Optional<List<Question>> questions = questionService.retrieveQuestionsByType(type);

        if (questions.isPresent()) {
            return new ResponseEntity<>(questions.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("")
	public ResponseEntity<Optional<List<Question>>> retrieveAllquestions(){
		Optional<List<Question>> questions = questionService.retrieveAllQuestions();
		
		if(questions.isPresent()) {
			return new ResponseEntity<>(questions, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}

}
