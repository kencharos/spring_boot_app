package jp.co.ulsystems.sb.login;

import javax.validation.Valid;

import jp.co.ulsystems.sb.auth.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@SessionAttributes("user") //セッションで使用する名称を指定。
public class LoginController {

	@Autowired
	private DummyLoginService loginService;
	
	@Autowired
	private AuthenticationManager am;
	
	@RequestMapping(value= "/login", method = RequestMethod.GET)
	public String login(LoginRequest req, Model model) {
		return "login";
    }
	
	@RequestMapping(value= "/login", method = RequestMethod.POST)
	public String doLogin(@Valid LoginRequest req, BindingResult res, 
			Model model) {
		
		AuthUser authUser = new AuthUser();
		// @SessionAttributesで指定した名称でオブジェクトを保存。
		model.addAttribute("user", authUser);
		
		if (res.hasErrors()) {
			return "login";
		}
		User user = loginService.login(req.getId(), req.getPassword());
		if (user == null) {
			res.addError(new ObjectError("global", "invalid id or password"));
			return "login";
		}
		authUser.setId(user.getId());
		
		return "redirect:/welcome";
    }
	@RequestMapping(value= "/logout", method = RequestMethod.GET)
	public String logout(SessionStatus status) {
		status.setComplete(); // セッション解放
		return "redirect:/";
    }
	
	
	@RequestMapping(value= "/welcome", method = RequestMethod.GET)
	public String welcome(@ModelAttribute("user")AuthUser authUser) {
		// セッションに格納したオブジェクトを @ModelAttributeアノテーションを引数に指定して取得可能。
		System.out.println(authUser.getId());
		return "welcome";
    }
	
}
