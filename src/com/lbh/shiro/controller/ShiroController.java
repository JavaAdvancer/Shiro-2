package com.lbh.shiro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lbh.shiro.service.ShiroService;

@Controller
@RequestMapping("/shiro")
public class ShiroController {
	
	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/testQXMethod")
	public String testQXMethod(HttpSession session){
		session.setAttribute("sessionkey", "sessionValue");
		System.out.println(shiroService.testQXMethod());
		return "redirect:/success.jsp";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, @RequestParam("userName") String userName, @RequestParam("password") String password){
		// 1. 获取当前Subject 
		Subject currentUser = SecurityUtils.getSubject();
		
		// 2.检测当前用户是否被认证， 即是否已登录。
        if (!currentUser.isAuthenticated()) {
        	// 将用户名密码 封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            // rememberMe
            token.setRememberMe(true);
            try {
            	System.out.println("1. "+ token.hashCode());
            	//执行登录
                currentUser.login(token);
            } 
            // 所有认证信息的父类
            catch (AuthenticationException ae) {
               System.out.println("认证失败： "+ ae.getMessage());
            }
        }
		
		return "redirect:/success.jsp";
	}
	
}
