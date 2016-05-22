package jp.co.ulsystems.sb.auth;

//認証済みユーザー情報。いずれ、SpringSecurityに対応させる
public class User {

	private String id;
	
	public User(String username){
		this.id = username;
	}
	
	// accessor
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
