package com.sds.janus.sample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

	@RequestMapping("/resources/user")
	public String user() {
		return "user";
	}
}
