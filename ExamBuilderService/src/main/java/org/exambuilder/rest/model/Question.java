package org.exambuilder.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false, length=250)
	private String description;
	
	@Column(name="option_A", length=250)
	private String choiceA;
	
	@Column(name="option_B", length=250)
	private String choiceB;
	
	@Column(name="option_C", length=250)
	private String choiceC;
	
	@Column(name="option_D", length=250)
	private String choiceD;
	
	@Column(nullable=false, length=1)
	private String answer;  // A or B or C or D
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String question) {
		this.description = question;
	}
	public String getChoiceA() {
		return choiceA;
	}
	public void setChoiceA(String choiceOne) {
		this.choiceA = choiceOne;
	}
	public String getChoiceB() {
		return choiceB;
	}
	public void setChoiceB(String choiceTwo) {
		this.choiceB = choiceTwo;
	}
	public String getChoiceC() {
		return choiceC;
	}
	public void setChoiceC(String choiceThree) {
		this.choiceC = choiceThree;
	}
	public String getChoiceD() {
		return choiceD;
	}
	public void setChoiceD(String choiceFour) {
		this.choiceD = choiceFour;
	}
	public String getCorrectAnswer() {
		return answer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.answer = correctAnswer;
	}
}
