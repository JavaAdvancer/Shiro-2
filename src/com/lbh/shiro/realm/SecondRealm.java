package com.lbh.shiro.realm;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.lbh.shiro.entity.UserAccount;
import com.lbh.shiro.service.UserService;

public class SecondRealm extends AuthenticatingRealm {
	
	private static final Logger log = Logger.getLogger(SecondRealm.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("This is SecondRealm.....");
		
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
		
		Object credentials = null;
		if ("lbh".equals(userName)) {
			credentials = "2a0bde567554d82ccb9d66975cdd56b26cfd856d";
		}else if ("jkj".equals(userName)) {
			credentials = "2a472422c310018ce8544b923f1bf66a844d987f";
		}
		
		// 6. 根据用户的情况，决定是否需要抛出其他的AuthenticationException异常
		//以下信息是从数据库中获取的 
		//1). principal: 认证的实体信息，可以是username， 也可以是数据表对应的用户实体类对象。
		Object principal = userName;
		//2). credentials: 密码
		//Object credentials = userAccount.getPassword();
		//3). credentialsSalt: 加盐
		ByteSource credentialsSalt = ByteSource.Util.bytes(userName); 
		//4). realmName: 当前 realm 对象的 name。 调用父类的 getName() 方法即可
		String realmName = getName();
		
		SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;
	}
	
	
	public static void main(String[] args) {
		String algorithmName = "SHA1";  //加密方法
		Object source = "123";  //明文密码
		Object salt = ByteSource.Util.bytes("jkj");  //加密的盐值
		int hashIterations = 10;  //加密次数
		
		Object result = new SimpleHash(algorithmName, source, salt, hashIterations);
		System.out.println(result);
	}

	/**
	 * 从前台输入的 字符串明文密码 经过 shiro 的 jdbcRealm 配置MD5 加密后，自动的将字符串加密为MD5加密后的密文字符串与 数据库中的密码对比。
	 * 前台密码的加密方式：  new SimpleHash(algorithmName, source, salt, hashIterations);
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
}
