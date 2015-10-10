package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping("oauth2/login")
	public String login(@RequestParam("redirect") String redirect) {
		System.out.println("redirect : " + redirect);
		return "redirect:"+redirect;
	}

}
