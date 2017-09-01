package org.exambuilder.rest.dao;

import java.util.List;

import org.exambuilder.rest.model.CandidateTest;
import org.exambuilder.rest.model.Test;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

@Repository("testDao")
public class TestDao extends BaseDao {

	public void save(Test test) {
		getSession().persist(test);
	}
	
	public List<Test> getAllTests() {
		return getSession().createCriteria(Test.class).list();
	}
	
	public Test getTestByKey(long id) {
		Test test = (Test) getSession().get(Test.class, id);
		Hibernate.initialize(test.getQuestions());
		return test;
	}

	public void saveCandidateTest(CandidateTest candidateTest) {
		getSession().persist(candidateTest);
	}
}
