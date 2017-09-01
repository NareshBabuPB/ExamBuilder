package org.exambuilder.rest.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
public class Test {

	@Id
	@GeneratedValue
	long id;
	
	@NotEmpty
	@Column(length=250)
	String name;
	
	@Min(1)
	@Column(name="duration_minutes", nullable=false)
	int durationMinutes;
	
	@Range(min=1, max=100)
	@Column(name="pass_score", nullable=false)
	int passScore;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name = "test_question", 
		joinColumns = {@JoinColumn(name="test_id")}, 
		inverseJoinColumns = {@JoinColumn(name="question_id")})
	List<Question> questions;
	
	@Transient
	CommonsMultipartFile uploadedQuestions;
	
	public Test() {
		questions = new ArrayList<>();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getName() {
		return name;
	}

	public void setName(String testName) {
		this.name = testName;
	}

	public int getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public int getPassScore() {
		return passScore;
	}

	public void setPassScore(int passScore) {
		this.passScore = passScore;
	}

	public CommonsMultipartFile getUploadedQuestions() {
		return uploadedQuestions;
	}

	public void setUploadedQuestions(CommonsMultipartFile uploadedQuestions) {
		this.uploadedQuestions = uploadedQuestions;
	}
}
