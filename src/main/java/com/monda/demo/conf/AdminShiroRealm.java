package com.monda.demo.conf;

import com.monda.demo.model.AdminRole;
import com.monda.demo.model.AdminUser;
import com.monda.demo.service.AdminUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 后台模块shiro权限认证
 * @author yangjian
 * @since 2017-08-25 下午10:01.
 */
public class AdminShiroRealm extends AuthorizingRealm {

	static Logger logger = LoggerFactory.getLogger(AdminShiroRealm.class);

	@Autowired
	AdminUserService adminUserService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		AdminUser adminUser = (AdminUser) principals.getPrimaryPrincipal();
		for(AdminRole role:adminUser.getRoleList()) {
			authorizationInfo.addRole("role_"+role.getId());
			Map<String, Object> permissionMap = role.getPermissionMap();
			logger.error("{}",permissionMap);
			role.getPermissionMap().forEach((k, v)-> authorizationInfo.addStringPermission(k));

		}
		return authorizationInfo;
	}

	/**
	 * 身份认证，验证用户输入的账号和密码是否正确。
	 * @param authcToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		//用户登陆认证，这里可以根据实际情况做缓存
		AdminUser loginUser = adminUserService.login(token.getUsername());
		if(null == loginUser){
			return null;
		}

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				loginUser, //用户名
				loginUser.getPassword(), //密码
				ByteSource.Util.bytes(loginUser.getCredentialsSalt()),//salt=username+salt
				getName()  //realm name
		);
		return authenticationInfo;
	}
}
