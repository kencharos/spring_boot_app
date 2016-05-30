package jp.co.ulsystems.sb.login;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.ulsystems.sb.auth.User;


@Controller
//　入力値をコントローラ単位でセッションに持つ場合、SessionAttributesを使う
//@SessionAttributes(types = LoginRequest.class) 
public class LoginController {

	@Autowired
	private DummyLoginService loginService;

	@Autowired
	private AuthUser authUser;
	
	@ModelAttribute
	public LoginRequest createForm(){
		// ModelAttributeを指定したメソッドは、リクエストを処理するメソッド実行前に呼ばれ、モデルの初期化処理を行う。
		return new LoginRequest();
	}

	
	@RequestMapping(value= "/login", method = RequestMethod.GET)
	public String login(LoginRequest req, Model model) {
		model.addAttribute("message", "input your id and passowd.");
		return "login";
    }
	
	@RequestMapping(value= "/login", method = RequestMethod.POST)
	public String doLogin(@Valid LoginRequest req, BindingResult res, 
			Model model) {
		if (res.hasErrors()) {
			return "login";
		}
		User user = loginService.login(req.getId(), req.getPassword());
		if (user == null) {
			res.addError(new ObjectError("global", "invalid id or password"));
			return "login";
		}
		authUser.setUserId(user.getId());
		
		return "redirect:/welcome";
    }
	
	@RequestMapping(value= "/logout", method = RequestMethod.GET)
	public String logout() {
		System.out.println("logout:" + authUser.getUserId());
		// セッション解放@SessionAttributesで定義されたオブジェクトが自動解放。
		// @Scope("session")のbeanをインジェクションで取得した場合は、解放されない。
		authUser.setUserId(null);
		return "redirect:/";
    }
	
	
	// MEMO
	// セッションを使用する方法はSpring MVCでは色々ある。
	// 1. コントローラメソッド引数に、HttpSessionを追加する。従来型の方法
	// 2. SessionAttributes,ModelAttributesを使用するSpring MVCの方法。これが自動解放などもあるし便利。 しかしviewでの参照で癖がある。
	// 3. Spring Beanの定義に、　@Scope("session")を追加して、インジェクションする方法。解放などはSpring 任せになる。　プロキシなどトラップ多し。
	
	// @ModelAtttibuteアノテーション付きのメソッドを使用することで、各メソッドにAuthUser型の引数をセッションスコープで追加できる。
	// ただし、@SessionAttibutesでどのような指定をしても、Viewでは型の暗黙名出しか参照できない。
	// 任意の名称でviewで参照させたい場合は以下の手順を取る。
	//   1.@SessionAttributes("任意名") で宣言。 
	//   2.コンとローラメソッド内で、 model.addAttribute("任意名", new HogeBean());
	//     のようにすると、@SeesoinAttributesと同じ名前のオブジェクトがセッションスコープで扱われるし、その名前でviewから参照可能。
	//   3. 別のコントローラメソッドでそのオブジェクトを使用するには、引数に、 @ModelAttributes("任意名") HogeBean bean
	//      のように、@ModelAttributesアノテーションを引数に入れる。

	
}
