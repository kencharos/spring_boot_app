package jp.co.ulsystems.sb.login;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ulsystems.sb.auth.LoginResult;


@RestController //(1) RestControllerを指定
public class LoginAjaxController {

	@Autowired
	private DummyLoginService loginService;
	
	// (2) consumes, produces で送受できる形式を指定
	@RequestMapping(value="/login/tryLogin", method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public LoginResult tryLogin(@Valid @RequestBody LoginRequest req
			, BindingResult res, HttpServletResponse response) {
		// (3) @RequestBody でJSONデータをマッピングするオブジェクトを指定
		
		LoginResult result = new LoginResult();
		if (res.hasErrors()) {
			// エラーメッセージを変換
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.messages = res.getAllErrors().stream()
					.map(er -> er.getCodes()[1]+ "-" + er.getDefaultMessage())
			.collect(Collectors.toList());
			return result;
		}
		
		if (loginService.login(req.getId(), req.getPassword()) == null) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.messages.add("invalid id or password");
			
		} else {
			result.success = true;
		}
		// (4) 返却したオブジェクトはJSONに変換する。
		return result;
		
	}
}
