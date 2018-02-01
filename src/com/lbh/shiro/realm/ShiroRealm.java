package com.lbh.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.lbh.shiro.entity.UserAccount;
import com.lbh.shiro.service.UserService;

public class ShiroRealm extends AuthorizingRealm {
	
	private static final Logger log = Logger.getLogger(ShiroRealm.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("This is FirstRealm.....");
		
		// 1. 把AuthenticationToken 转换为 UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;		
		// 2. 从UsernamePasswordToken 中来获取 username
		String userName = upToken.getUsername();		
		// 3. 调用数据库的方法， 从数据库中查询 username 对应的用户记录
		UserAccount userAccount = userService.findUserbyName(userName);
		log.info("从数据库中获取 username：" + userName + "所对应的记录。");			
		// 4. 若用户不存在，则可以抛出 UnknownAccountException 异常		
		if (null == userAccount || (userAccount.getUserId() == null || userAccount.getUserId().equals(""))) {
			throw new UnknownAccountException("用户不存在！");
		}
/*		Shiro 自带密码对比功能
		System.out.println("库中密码： "+ userAccount.getPassword());
		System.out.println("输入密码： "+ String.valueOf(upToken.getPassword()));  // 密码 char[] ——> String
		if (!userAccount.getPassword().equals(String.valueOf(upToken.getPassword()))) {
			throw new IncorrectCredentialsException("密码不正确");
		}*/		
		// 5. 根据用户信息情况，决定是否需要抛出其他的AuthenticationException 异常
		if ("Y".equals(String.valueOf(userAccount.getIsLocked()))) {
			throw new LockedAccountException("用户被锁定");
		}
		
		// 6. 根据用户的情况，决定是否需要抛出其他的AuthenticationException异常
		//以下信息是从数据库中获取的 
		//1). principal: 认证的实体信息，可以是username， 也可以是数据表对应的用户实体类对象。
		Object principal = userAccount.getUserName();
		//2). credentials: 密码
		Object credentials = userAccount.getPassword();
		//3). credentialsSalt: 加盐
		ByteSource credentialsSalt = ByteSource.Util.bytes(userName); 
		//4). realmName: 当前 realm 对象的 name。 调用父类的 getName() 方法即可
		String realmName = getName();
		
		SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;
	}
	
	
	public static void main(String[] args) {
		String algorithmName = "MD5";  //加密方法
		Object source = "123456";  //明文密码
		Object salt = ByteSource.Util.bytes("admin");  //加密的盐值
		int hashIterations = 10;  //加密次数
		
		Object result = new SimpleHash(algorithmName, source, salt, hashIterations);
		System.out.println(result);
	}


	// 授权会被 shiro 回调的方法 
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权会被 shiro 回调的方法 ");
		//1. 从PrincipalCollenction 中来获取登录用户的信息
		Object principal = principals.getPrimaryPrincipal();
		
		//2. 利用登录的用户的信息来获取当前查询角色或权限(可能需要查询数据库)
		Set<String> roles = new HashSet<>();
		roles.add("user");
		if("lbh".equals(principal)){
			roles.add("admin");
		}
		
		// 3. 创建 SimpleAuthorizationInfo, 并设置其 roles 属性
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		
		return info;
	}

	/**
	 * 从前台输入的 字符串明文密码 经过 shiro 的 jdbcRealm 配置MD5 加密后，自动的将字符串加密为MD5加密后的密文字符串与 数据库中的密码对比。
	 * 前台密码的加密方式：  new SimpleHash(algorithmName, source, salt, hashIterations);
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
}
