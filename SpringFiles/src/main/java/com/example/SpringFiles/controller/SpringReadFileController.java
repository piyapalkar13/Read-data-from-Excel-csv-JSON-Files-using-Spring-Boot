package com.example.SpringFiles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.SpringFiles.service.ReadFileService;

@Controller
public class SpringReadFileController {

	@Autowired
	private ReadFileService sr;
}
