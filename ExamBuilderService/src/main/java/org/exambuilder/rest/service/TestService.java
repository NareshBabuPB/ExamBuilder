package org.exambuilder.rest.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.exambuilder.rest.dao.TestDao;
import org.exambuilder.rest.model.CandidateTest;
import org.exambuilder.rest.model.Question;
import org.exambuilder.rest.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("testService")
public class TestService {
	
	@Autowired
	private TestDao testDao;	

	public void createTest(Test test) {
		try {
			CSVParser parser 
				= CSVFormat.EXCEL.withFirstRecordAsHeader()
								.parse(new InputStreamReader(test.getUploadedQuestions().getInputStream()));
			
			parser.getRecords().forEach(record -> {
				Question question = new Question();
				question.setDescription(record.get("Question"));
				question.setChoiceA(record.get("Option A"));
				question.setChoiceB(record.get("Option B"));
				question.setChoiceC(record.get("Option C"));
				question.setChoiceD(record.get("Option D"));
				question.setCorrectAnswer(record.get("Answer"));
				
				test.getQuestions().add(question);
			});
			
			testDao.save(test);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Test> getAllTests() {
		return testDao.getAllTests();
	}
	
	public Test getTestByKey(long id) {
		return testDao.getTestByKey(id);
	}
	
	public void saveCandidateAnswers(CandidateTest candidateTest) {
		
		List<Question> testQuestions = candidateTest.getTest().getQuestions();
		int correctAnswersCount = validateAnswers(testQuestions, candidateTest.getAnsweredQuestions());
		candidateTest.setScore(correctAnswersCount);
		
		int passScore = candidateTest.getTest().getPassScore();
		int result = (correctAnswersCount / testQuestions.size()) * 100;
		candidateTest.setPassFlag(result >= passScore);
		
		testDao.saveCandidateTest(candidateTest);
	}
	
	private int validateAnswers(List<Question> questions, Map<Question, String> answers) {
		
		Long correctAnswersCount = questions
				.stream()
				.filter(Q -> Q.getCorrectAnswer().equals(answers.get(Q)))
				.count();
		
		return correctAnswersCount.intValue();
	}
}
