package com.sds.janus.sample.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/loginpage")
	public String loginPage(
			@RequestParam(value="redirect", required=false) String redirect,
			@RequestParam(value="type", required=false) String type,
				Model model) {
		model.addAttribute("redirect", redirect);
		model.addAttribute("type", type);
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			@RequestParam("id") String id,
			@RequestParam("password") String password,
			@RequestParam(value="redirect", required=false) String redirect,
			@RequestParam(value="type", required=false) String type,
			HttpSession session) throws UnsupportedEncodingException {
			
		logger.info("id: {}",id);
		logger.info("type: {}", type);
		logger.info("password: {}",password);
		logger.info("redirect: {}",redirect);
		
		String redirectUrl = null;
		
		session.setAttribute("login", "true");
		
		switch(type) {
		case "oauth":
			redirectUrl = "permissionpage?redirect="+URLEncoder.encode(redirect,"UTF-8");
			break;
		default:
			redirectUrl=redirect;
		}
		
		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value="/permissionpage")
	public String permissionPage(
			@RequestParam("redirect") String redirect,
			Model model) {
		model.addAttribute("redirect", redirect);
		logger.info("redirect: {}",redirect);
		return "permission";		
	}
	
	@RequestMapping(value="/permission", method=RequestMethod.POST)
	public String permission(
			@RequestParam(value="redirect", required=false) String redirect
			) {
		return "redirect:" + redirect;
	}
	
}
