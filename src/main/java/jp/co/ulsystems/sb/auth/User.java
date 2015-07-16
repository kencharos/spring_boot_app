package jp.co.ulsystems.sb.auth;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

//認証済みユーザー情報の拡張実装。ただし、今のところSpringSecurityの拡張部分は未使用。
@Entity
public class User extends org.springframework.security.core.userdetails.User {

	@Id
	@Column
	private String id;
	
	@Column
	private String hashPassword;
	
	public User() {
		//TODO 後でForm認証を使用する際に重要になる。
		super("invalid", "invalid", AuthorityUtils.createAuthorityList("normal"));
	}
	
	public User(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = username;
		this.hashPassword = password;
	}
	
	// accessor
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}
    
	
}
