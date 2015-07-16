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
//セッションで使用する名称または型を指定。
@SessionAttributes(types = AuthUser.class) 
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
			Model model, AuthUser authUser) {
		
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
	public String logout(SessionStatus status,AuthUser authUser) {
		System.out.println("logout:" + authUser.getId());
		// セッション解放@SessionAttributesで定義されたオブジェクトが自動解放。
		// @Scope("session")のbeanをインジェクションで取得した場合は、解放されない。
		status.setComplete();
		return "redirect:/";
    }
	
	
	@RequestMapping(value= "/welcome", method = RequestMethod.GET)
	public String welcome(AuthUser authUser) {
		// セッションに格納したオブジェクトを @ModelAttributeアノテーションを引数に指定して取得可能。
		System.out.println("welcome:" + authUser.getId());
		return "welcome";
    }
	
	// セッションを使用する方法はSpring MVCでは色々ある。
	// 1. コントローラメソッド引数に、HttpSessionを追加する。従来型の方法
	// 2. SessionAttributes,ModelAttributesを使用するSpring MVCの方法。これが自動解放などもあるし便利? でもviewでの参照で癖がある。
	// 3. Spring Beanの定義に、　@Scope("session")を追加して、インジェクションする方法。解放などはSpring 任せになる。　プロキシなどトラップ多し。
	
	// @ModelAtttibuteアノテーション付きのメソッドを使用することで、各メソッドにAuthUser型の引数をセッションスコープで追加できる。
	// ただし、@SessionAttibutesでどのような指定をしても、Viewでは型の暗黙名出しか参照できない。
	// 任意の名称でviewで参照させたい場合は以下の手順を取る。
	//   1.@SessionAttributes("任意名") で宣言。 
	//   2.コンとローラメソッド内で、 model.addAttribute("任意名", new HogeBean());
	//     のようにすると、@SeesoinAttributesと同じ名前のオブジェクトがセッションスコープで扱われるし、その名前でviewから参照可能。
	//   3. 別のコントローラメソッドでそのオブジェクトを使用するには、引数に、 @ModelAttributes("任意名") HogeBean bean
	//      のように、@ModelAttributesアノテーションを引数に入れる。
	//   4. この場合、↓のメソッドはいらない。一長一短だな。。
	@ModelAttribute("authUser") 
	public AuthUser createAuthUser() {
		System.out.println("crelete session model");
		return new AuthUser();
	}		
	
}
