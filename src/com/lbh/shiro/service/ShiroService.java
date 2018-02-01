package com.lbh.shiro.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

@Service
public class ShiroService {
	
	/** 注意： 
	 * 角色权限注解 不能与 @Transactional 事务注解共同放在Serice层，否则会报类型转换异常，
	 * 放在 controller 层中
	 * @return
	 */
	
	@RequiresRoles({"admin"})
	public String testQXMethod(){
		/*
		 * 在传统的web应用中，我们无法在Service中访问HttpSession (那样是侵入式的)，而shiro提供的Session可以。
		 *在控制层中用的 HttpSession, 而在Service层可以使用 Shiro 给提供的 session,即使是在service中也可以使用session中的数据
		 */
		Session session = SecurityUtils.getSubject().getSession();
		Object val = session.getAttribute("sessionkey");
		System.out.println("Sessoin 值："+ val);
		return "当前时间： "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

}
