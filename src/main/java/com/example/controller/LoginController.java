package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.service.LoginService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public String login(@RequestParam("name") String name, HttpSession session,
			@RequestParam("password") String password) {
		boolean isAuthenticated = loginService.authenticate(name, password);
		if (isAuthenticated) {
			session.setAttribute("loginUser", name);
			return "rooms";
		} else {
			return "redirect:/login?error";
		}
	}

}