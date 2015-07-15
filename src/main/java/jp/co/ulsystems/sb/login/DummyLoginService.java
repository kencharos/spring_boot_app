package jp.co.ulsystems.sb.login;

import jp.co.ulsystems.sb.auth.User;
import jp.co.ulsystems.sb.auth.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * SpringSecurityを使用せず、自前で認証を行うためのサービス。
 * いずれ、削除しSpringSecurityを使用する。
 *
 */
@Service
public class DummyLoginService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public User login(String id, String password) {
		User user = repo.findOne(id);
		if (user == null || !encoder.matches(password, user.getHashPassword())) {
			return null;
		} else {
			return user;
		}
		
	}
	
	
}
