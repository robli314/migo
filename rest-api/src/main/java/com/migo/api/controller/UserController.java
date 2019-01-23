package com.migo.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	public UserController() {

	}
	
	@RequestMapping("/hello")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return "Hi, there!";
    }

}
