package com.lbh.shiro.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lbh.shiro.dao.UserDao;
import com.lbh.shiro.entity.UserAccount;

/**
 * 用户 服务层
 * @author Advancer
 *
 */
@Service
public class UserService {
	
	@Resource
	private UserDao userDao;
	
	public UserAccount findUserbyName(String userName){
		return userDao.findUserbyName(userName);
	}
	
}













