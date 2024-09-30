package com.exam.test_api;

import java.util.ArrayList;
import java.util.List;

import com.exam.test_api.model.Answer;
import com.exam.test_api.model.Question;

public class testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Question> questionList = new ArrayList<>();

		
		questionList.add(new Question((long) 1, "Which of these is not an animal?", "test", List.of("frog", "bat", "bird", "gun"), "gun"));
		questionList.add(new Question((long) 2, "What is the capital of Japan?", "geography", List.of("Tokyo", "Seoul", "Beijing", "Bangkok"), "Tokyo"));
		questionList.add(new Question((long) 3, "Which planet is known as the Red Planet?", "science", List.of("Earth", "Mars", "Jupiter", "Saturn"), "Mars"));
		questionList.add(new Question((long) 4, "What is H2O commonly known as?", "chemistry", List.of("Oxygen", "Hydrogen", "Water", "Carbon Dioxide"), "Water"));
		questionList.add(new Question((long) 5, "Who wrote 'Romeo and Juliet'?", "literature", List.of("Charles Dickens", "Jane Austen", "Mark Twain", "William Shakespeare"), "William Shakespeare"));
		questionList.add(new Question((long) 6, "What is the largest mammal?", "biology", List.of("Elephant", "Whale", "Giraffe", "Hippopotamus"), "Whale"));
		questionList.add(new Question((long) 7, "Which element is represented by the symbol 'O'?", "chemistry", List.of("Gold", "Oxygen", "Silver", "Iron"), "Oxygen"));
		questionList.add(new Question((long) 8, "Who was the first President of the United States?", "history", List.of("Abraham Lincoln", "George Washington", "Thomas Jefferson", "John Adams"), "George Washington"));
		questionList.add(new Question((long) 9, "What is the boiling point of water at sea level?", "science", List.of("90°C", "100°C", "110°C", "120°C"), "100°C"));
		questionList.add(new Question((long) 10, "Which language is used for Android app development?", "technology", List.of("Python", "Java", "C#", "Ruby"), "Java"));
		questionList.add(new Question((long) 11, "Which organ pumps blood throughout the body?", "biology", List.of("Lungs", "Liver", "Heart", "Kidney"), "Heart"));
		
        final long[] score = {0};
		
		List<Answer> answers = new ArrayList<>();
		
		answers.add(new Answer((long) 1, "gun"));
		answers.add(new Answer((long) 4, "water"));
		answers.add(new Answer((long) 3, "gun"));
		answers.add(new Answer((long) 2, "tokyo"));
		answers.add(new Answer((long) 5, "gun"));
		answers.add(new Answer((long) 6, "gun"));
		answers.add(new Answer((long) 7, "gun"));
		answers.add(new Answer((long) 8, "gun"));
		answers.add(new Answer((long) 9, "gun"));
		answers.add(new Answer((long) 10, "gun"));
		answers.add(new Answer((long) 11, "gun"));
		
		for(Question question: questionList) {
			
			answers.stream().filter(answer -> answer.getId().equals(question.getId())).findFirst().ifPresent(answer -> {
				if(answer.getAnswer().toLowerCase().equals(question.getCorrectAnswer().toLowerCase())) {
					
                    score[0]++;
				}
			});
		}
		System.out.println(score[0]);
	}

}
