package jp.co.ulsystems.sb.login;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jp.co.ulsystems.sb.auth.LogingResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RestController
public class LoginAjaxController {

	@Autowired
	private DummyLoginService loginService;
	
	
	@RequestMapping(value="/login/tryLogin", method=RequestMethod.POST)
	public @ResponseBody LogingResult tryLogin(@Valid @RequestBody LoginRequest req
			, BindingResult res, HttpServletResponse response) {
		
		LogingResult result = new LogingResult();
		if (res.hasErrors()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.messages = res.getAllErrors().stream().map(er -> er.getCodes()[1]+ "-" + er.getDefaultMessage())
			.collect(Collectors.toList());
			return result;
		}
		
		if (loginService.login(req.getId(), req.getPassword()) == null) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.messages.add("invalid id or password");
			
		} else {
			result.success = true;
		}
		return result;
		
	}
}
