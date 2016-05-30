package jp.co.ulsystems.sb.login;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {

	@Autowired
	private AuthUser user;
	
	@RequestMapping(value= "/welcome", method = RequestMethod.GET)
	public String welcome(Model model, HttpSession req) {
		Enumeration<String> e = req.getAttributeNames();
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		model.addAttribute("userId", user.getUserId());
		model.addAttribute("notifications", 
				Arrays.asList("This is Spring-boot application","note"));
		
		return "welcome";
    }
}
