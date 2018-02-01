package com.lbh.shiro.dao;

import com.lbh.shiro.entity.UserAccount;

/**
 * 用户 数据层
 * @author Advancer
 *
 */
public interface UserDao {
	
	UserAccount findUserbyName(String userName); 
	
}
