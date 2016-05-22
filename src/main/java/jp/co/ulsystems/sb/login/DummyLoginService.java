package jp.co.ulsystems.sb.login;

import org.springframework.stereotype.Service;

import jp.co.ulsystems.sb.auth.User;

/**
 * SpringSecurityを使用せず、ダミー認証を行うためのサービス。
 * いずれ、SpringSecurityを使用する。
 *
 */
@Service
public class DummyLoginService {
	
	public User login(String id, String password) {
		if(id.equals("user1") && password.equals("user1")) {
			return new User(id);
		} else {
			return null;
		}
	}
	
	
}
