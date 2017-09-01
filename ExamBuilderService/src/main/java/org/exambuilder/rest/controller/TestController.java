package org.exambuilder.rest.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.exambuilder.rest.model.CandidateTest;
import org.exambuilder.rest.model.Question;
import org.exambuilder.rest.model.Test;
import org.exambuilder.rest.model.User;
import org.exambuilder.rest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	
	@Autowired
	TestService testService;

	@GetMapping(path="/createTest")
	public ModelAndView createTest() {
		Test test = new Test();
		return new ModelAndView("createTest", "test", test);
	}
	
	@GetMapping(path = "/downloadTemplate", 
			produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody byte[] downloadTemplate(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setHeader("Content-Disposition", "attachment; filename=\"Questions_Template.csv\"");

		InputStream fileInputStream = request.getServletContext()
				.getResourceAsStream("/WEB-INF/resources/Questions_Template.csv");

		return IOUtils.toByteArray(fileInputStream);

	}
	
	@RequestMapping(path="/saveTest", method=RequestMethod.POST)
	public ModelAndView saveTest(@ModelAttribute("test") Test test) {
		// Validate file extension
		// Validate header line
		testService.createTest(test);
		return new ModelAndView("redirect:/");
	}
	
	@GetMapping(path="/viewTest/{id}")
	public ModelAndView viewTest(@PathVariable("id") long id, Model model) {
		Test test = testService.getTestByKey(id);
		
		return new ModelAndView("viewTest", "test", test);
	}
	
	@GetMapping(path="/takeTest/{id}")
	public ModelAndView takeTest(@PathVariable("id") long id, HttpSession session, Authentication auth) {
		Test test = testService.getTestByKey(id);
		
		CandidateTest candidateTest = new CandidateTest();
		candidateTest.setTest(test);
		candidateTest.setCandidate((User)auth.getPrincipal());
		
		candidateTest.setCurrentQuestion(test.getQuestions().get(0));
		candidateTest.setAnsweredQuestions(new HashMap());
		candidateTest.setTestDate(new Date());
		
		session.setAttribute("candidateTest_session", candidateTest);
		session.setAttribute("questionIndex", 0);

		return new ModelAndView("takeTest", "currentQuestion", candidateTest.getCurrentQuestion());
	}
	
	@PostMapping(path="/takeTest/next")
	public ModelAndView nextQuestion(@RequestParam("currentAnswer") String currentAnswer, HttpSession session) {
		
		CandidateTest sessionTest = (CandidateTest)session.getAttribute("candidateTest_session");
		sessionTest.getAnsweredQuestions().put(sessionTest.getCurrentQuestion(), currentAnswer);
		
		Test test = sessionTest.getTest();
		int questionIndex = (int) session.getAttribute("questionIndex");
		List<Question> questions = test.getQuestions();
		
		sessionTest.setCurrentQuestion(questions.get(++questionIndex));
		session.setAttribute("questionIndex", questionIndex);
		
		Map<String, Object> modelAttributes = new HashMap<>();
		if(questionIndex == questions.size() - 1)
			modelAttributes.put("lastQuestion", Boolean.TRUE);
		
		modelAttributes.put("currentQuestion", sessionTest.getCurrentQuestion());
		
		return new ModelAndView("takeTest", modelAttributes);
	}
	
	@PostMapping(path="/takeTest/submit")
	public ModelAndView submitAnswers(@RequestParam("currentAnswer") String currentAnswer, HttpSession session) {
		
		CandidateTest sessionTest = (CandidateTest)session.getAttribute("candidateTest_session");
		sessionTest.getAnsweredQuestions().put(sessionTest.getCurrentQuestion(), currentAnswer);
		
		testService.saveCandidateAnswers(sessionTest);
		
		session.removeAttribute("questionIndex");
		session.removeAttribute("candidateTest_session");
		
		return new ModelAndView("testResult", "passed", sessionTest.isPassFlag());
	}
}
