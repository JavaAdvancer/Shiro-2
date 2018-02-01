package com.lbh.shiro.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author Advancer
 *
 */
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;  //用户id
	private String userName; //用户名
	private String password; //密码
	private Date registerTime; //注册时间
	private Integer registerIp; //IP地址
	private char isLocked;  //用户是否锁定

	public char getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(char isLocked) {
		this.isLocked = isLocked;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(Integer registerIp) {
		this.registerIp = registerIp;
	}

	@Override
	public String toString() {
		return "UserAccount [userId=" + userId + ", userName=" + userName + ", password=" + password + ", registerTime="
				+ registerTime + ", registerIp=" + registerIp + ", isLocked=" + isLocked + "]";
	}

	

}
