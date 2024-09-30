package com.exam.test_api.model;


public class Answer {
	private Long id;
	private String answer;
	
	
	
	public Answer(Long id, String answer) {
		super();
		this.id = id;
		this.answer = answer;
	}
	
	public Answer() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", answer=" + answer + "]";
	}
	
	
	
}
