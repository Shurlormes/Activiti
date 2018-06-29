package com.voidforce.activiti.controller;

import com.voidforce.activiti.bean.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("http://localhost:3000")
public class LoginController {
	@GetMapping("/")
	public String login() {
		return "login";
	}

	@PostMapping("/post")
	@ResponseBody
	public String doPost(@ModelAttribute UserInfo userInfo) {
		return "success";
	}

	@PutMapping("/put")
	@ResponseBody
	public String doPut(@ModelAttribute UserInfo userInfo, @RequestParam(required = false) Boolean remember) {
		return "success";
	}

	@DeleteMapping("/delete")
	@ResponseBody
	public String doDelete() {
		return "success";
	}

	@GetMapping("/get")
	@ResponseBody
	public String doGet(@RequestParam String username, @RequestParam String password) {
		return "success";
	}
}
