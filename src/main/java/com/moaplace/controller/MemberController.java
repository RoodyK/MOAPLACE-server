package com.moaplace.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MemberController {

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> login(@RequestParam String id, int age) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("age", age);
		
		return map;
	}
}
