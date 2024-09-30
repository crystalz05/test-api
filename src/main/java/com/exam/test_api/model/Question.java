package com.exam.test_api.model;

import java.util.Collections;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String type;

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option_value ")
    private List<String> options;

    private String correctAnswer;

    // Constructors, Getters, and Setters

    public Question() {
    }

    public Question(Long id, String text, String type, List<String> options, String correctAnswer) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

	public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    @Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", type=" + type + ", options=" + options + ", correctAnswer="
				+ correctAnswer + "]";
	}
}
