package com.example.SpringFiles.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.SpringFiles.model.User;
import com.example.SpringFiles.service.ReadFileService;

@Controller
public class SpringReadFileController {

	@Autowired
	private ReadFileService sr;

	@GetMapping("/")
	public ModelAndView home()
	{
		ModelAndView m=new ModelAndView();
		m.addObject("user", new User());
		List<User> users=sr.findAll();
		m.addObject("users", users);
		m.setViewName("/view/users");
		
		return m;
}
}
