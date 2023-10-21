package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
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
    	model.addAttribute("user",new User());
    	return "signup";
    }
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user, @RequestParam(value = "aggrement",defaultValue = "false") boolean aggrement,Model model, BindingResult result,HttpSession session) {
		
		try {
			if(!aggrement) {
				System.out.println("please agree terms and conditions");
				throw new Exception(", please agree terms and conditions...");
			}
			if(result.hasErrors()) {
				System.out.println("Error"+result.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.jpg");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.println(aggrement);
			System.out.println(user);
			User results=this.userRepository.save(user);
			model.addAttribute("user",new User());
			session.setAttribute("message", new Message("Successfully registered...!!!","alert-success"));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			
			session.setAttribute("message", new Message("Something went wrong"+e.getMessage(),"alert-danger"));
			return "signup";
		}
		
	}
}
