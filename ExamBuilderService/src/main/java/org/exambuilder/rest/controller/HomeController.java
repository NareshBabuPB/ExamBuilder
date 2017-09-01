package org.exambuilder.rest.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.exambuilder.rest.model.Test;
import org.exambuilder.rest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/")
public class HomeController {
	
	@Autowired
	TestService testService;

	@GetMapping
	public String loadHomePage(Model model, Principal principal, HttpServletRequest request) {
		List<Test> tests = testService.getAllTests();
		model.addAttribute("tests", tests);
		
		System.out.println("Principal method parameter: " + principal.getName());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated())
			System.out.println("SecurityContextHolder: " + auth.getName());
		
		UserDetails user = (UserDetails) auth.getPrincipal();
		System.out.println("Type casting Principal to UserDetails: " + user.getUsername());
		
		System.out.println("Request UserPrincipal: " + request.getUserPrincipal().getName());
		
		return "home";
	}
}
