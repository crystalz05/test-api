package com.exam.test_api.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.exam.test_api.model.Exam;
import com.exam.test_api.model.Question;
import com.exam.test_api.repositories.ExamRepository;

import jakarta.transaction.Transactional;

@Service
public class ExamService {

	private ExamRepository examRepository;
	private QuestionService questionService;

	public ExamService(ExamRepository examRepository, QuestionService questionService) {
		super();
		this.examRepository = examRepository;
		this.questionService = questionService;
	}

	
	//no need to add questions on creation
	public Long createExam(Exam exam) {
		
		try {
			Optional<List<Question>> questions = questionService.retrieveQuestionsByType(exam.getExamType());
			List<Question> question1 = questions.get();
			
			if(question1.isEmpty()) {
				return -1L;
			}
			for(Question question : question1) {
		        List<String> option = question.getOptions();
		        Collections.shuffle(option);
				question.setOptions(option);
			}
			exam.setQuestions(question1);
			Exam finalSaved = examRepository.save(exam);
			return finalSaved.getId();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1L;
	}

	@Transactional
	public boolean deleteExam(Long id) {
		if (examRepository.existsById(id)) {
			Optional<Exam> examOptional = examRepository.findById(id);
			if (examOptional.isPresent()) {
				Exam exam = examOptional.get();
				exam.setQuestions(null); // Clear the list of questions
				examRepository.save(exam); // Save the exam to update the relationship
				examRepository.deleteById(id); // Delete the exam
				return true;
			}
		}
		return false;
	}

	public Optional<Exam> retrieveExamById(Long id) {
		return examRepository.findById(id);
	}
	
	public List<Exam> retriveAllExams(){
		return examRepository.findAll();
	}
	
	public void deleteAllExams() {
		examRepository.deleteAll();
	}

}
