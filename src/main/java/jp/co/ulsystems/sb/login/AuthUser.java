package jp.co.ulsystems.sb.login;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

// SpringCoreの機能を使用してセッションスコープのBeanをインジェクションさせる場合は、@Component,@Scopeアノテーションを使う。
// インジェクションした場合スコープ制御はSpringが行うので、MVC側の SesionStatus#setCompleteによる破棄は不可。
@Component
// proxyモードはシングルトンスコープのコントローラにインジェクションさせるために必要
@Scope(value=WebApplicationContext.SCOPE_SESSION, proxyMode=ScopedProxyMode.TARGET_CLASS)
public class AuthUser implements Serializable {

	private String userId;

	public boolean isLogin() {
		return userId != null && userId.length() > 0;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
