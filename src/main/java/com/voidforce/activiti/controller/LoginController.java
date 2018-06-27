package com.voidforce.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
	@GetMapping("/")
	public String login() {
		return "redirect:http://localhost:3000/";
	}

	@PostMapping("/doLogin")
	@ResponseBody
	public String doLogin(@RequestParam(required = false) String username, @RequestParam(required = false) String password) {
		return "success:" + username + ", " + password;
	}

	@GetMapping("/doLogin")
	@ResponseBody
	public String doLoginGet(@RequestParam(required = false) String username, @RequestParam(required = false) String password) {
		return "success:" + username + ", " + password;
	}
}
