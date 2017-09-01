package org.exambuilder.rest.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="test_candidate")
public class CandidateTest {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="test_id")
	private Test test;
	
	@ManyToOne
	@JoinColumn(name="candidate_id")
	private User candidate;

	private Integer score;

	@Column(name="test_date")
	private Date testDate;
	
	@Column(name="pass_flag")
	private boolean passFlag;
	
	@ElementCollection
	@CollectionTable(name="candidate_answers", joinColumns=@JoinColumn(name="test_candidate_id"))
	@MapKeyColumn(name="question_id")
	@Column(name="given_answer")
	private Map<Question, String> answeredQuestions;

	@Transient
	private Question currentQuestion;
	
	@Transient
	private String currentAnswer;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public User getCandidate() {
		return candidate;
	}

	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public boolean isPassFlag() {
		return passFlag;
	}

	public void setPassFlag(boolean passFlag) {
		this.passFlag = passFlag;
	}

	public Map<Question, String> getAnsweredQuestions() {
		return answeredQuestions;
	}

	public void setAnsweredQuestions(Map<Question, String> answeredQuestions) {
		this.answeredQuestions = answeredQuestions;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public String getCurrentAnswer() {
		return currentAnswer;
	}

	public void setCurrentAnswer(String currentAnswer) {
		this.currentAnswer = currentAnswer;
	}
}
