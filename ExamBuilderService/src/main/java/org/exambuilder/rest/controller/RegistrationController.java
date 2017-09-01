package org.exambuilder.rest.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.validation.Valid;

import org.exambuilder.rest.dao.UserDao;
import org.exambuilder.rest.model.Role;
import org.exambuilder.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {
	
	@Autowired
	UserDao userDao;
	
	@GetMapping("/user/registration")
	public ModelAndView loadRegistrationPage() {
	
		User userDTO = new User();
		return new ModelAndView("registration", "user", userDTO);
	}
	
	@PostMapping("/user/registration")
	public ModelAndView registerUser(@Valid @ModelAttribute("user") User user,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return new ModelAndView("registration", "user", user);
		}
		
		user.setEnabled(true);
		user.setRoles(Arrays.asList(Role.USER));

		userDao.saveUser(user);
		
		return new ModelAndView("redirect:/login");
	}
	
	@InitBinder
	public void bindingPreparation(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("MMM d, YYYY");
		CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, orderDateEditor);
	}
}
