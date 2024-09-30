package com.exam.test_api.services;

import java.util.Collections;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.exam.test_api.model.Question;
import com.exam.test_api.repositories.QuestionsRepository;

@Service
public class QuestionService {

	private QuestionsRepository questionsRepository;

	public QuestionService(QuestionsRepository questionsRepository) {
		super();
		this.questionsRepository = questionsRepository;
	}

	public Question createQuestion(Question question) {
		return questionsRepository.save(question);
	}
	
	public List<Question> createMultipleQuestions(List<Question> questions){
		return questionsRepository.saveAll(questions);
	}

	public void deleteQuestion(Long id) {
		questionsRepository.deleteById(id);
	}
	
	
	public Optional<Question> retrieveQuestionById(Long id) {
		return questionsRepository.findById(id);
	}
	
	public Optional<List<Question>> retrieveQuestionsByType(String type) {
		
		List<Question> questions = questionsRepository.findAll();
	    Collections.shuffle(questions);
		List<Question> filteredQuestions = questions.stream().filter(question -> question.getType().equalsIgnoreCase(type)).limit(20).collect(Collectors.toList());
	    return Optional.of(filteredQuestions);
	   
	}
	
	public Optional<List<Question>> retrieveAllQuestions(){
		return Optional.of(questionsRepository.findAll());
	}
	
	

}
