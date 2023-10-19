package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
    public String Home(Model model) {
    	model.addAttribute("title","Home- Smart Contact Manager");
    	return "home";
    }
	@RequestMapping("/about")
    public String About(Model model) {
    	model.addAttribute("title","about");
    	return "about";
    }
	@RequestMapping("/signup")
    public String signUp(Model model) {
    	model.addAttribute("title","Register");
    	return "signup";
    }
}
