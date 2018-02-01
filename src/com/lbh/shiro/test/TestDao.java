package com.lbh.shiro.test;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lbh.shiro.dao.UserDao;
import com.lbh.shiro.entity.UserAccount;

/**
 * 测试是否通过
 * @author Advancer
 *
 */
public class TestDao {
	
	private ApplicationContext ax;
	private UserDao userDao;
	
	private static final Logger log = Logger.getLogger(TestDao.class);
	
	@Before
	public void init(){
		ax = new ClassPathXmlApplicationContext("applicationContext.xml");
		userDao = ax.getBean("userDao", UserDao.class);
	}
	
	@Test
	public void findUserByName(){
		UserAccount userAccount = userDao.findUserbyName("lbh");
		log.info("获取用户名： "+ userAccount);
		System.out.println(userAccount);
	}

}










