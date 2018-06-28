package com.voidforce.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("http://localhost:3000")
public class LoginController {
	@GetMapping("/")
	public String login() {
		return "redirect:http://localhost:3000/";
	}

	@PostMapping("/post")
	@ResponseBody
	public String doPost() {
		return "success";
	}

	@PutMapping("/put")
	@ResponseBody
	public String doPut() {
		return "success";
	}

	@DeleteMapping("/delete")
	@ResponseBody
	public String doDelete() {
		return "success";
	}

	@GetMapping("/get")
	@ResponseBody
	public String doGet() {
		return "success";
	}
}
