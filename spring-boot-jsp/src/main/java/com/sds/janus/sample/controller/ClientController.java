package com.sds.janus.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientController {

	@RequestMapping("/redirect")
	public String redirect(@RequestParam("code") String code,
			RedirectAttributes redirectAttr) {

		redirectAttr.addAttribute("grant_type", "authorization_code");
		redirectAttr.addAttribute("client_id", "younggi");
		redirectAttr.addAttribute("client_secret", "secret");
		redirectAttr.addAttribute("code", code);
		redirectAttr.addAttribute("redirect_uri","http://www.naver.com");
		
		return "redirect:/oauth2/token";
	}

}
