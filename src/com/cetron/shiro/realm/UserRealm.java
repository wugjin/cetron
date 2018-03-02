package com.cetron.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm {
	
	/**
	 * Shiro权限认证，即权限授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		return null;
	}

	/**
	 * 用户认证,登录认证(原理：用户提交 用户名和密码 --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ----
	 * shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登录控制 )
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		return null;
	}
	
}
