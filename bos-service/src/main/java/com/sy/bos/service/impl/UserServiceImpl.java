package com.sy.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sy.bos.dao.UserDao;
import com.sy.bos.domain.User;
import com.sy.bos.service.UserService;
import com.sy.bos.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User login(User user) {
		String passwd = MD5Utils.md5(user.getPassword());

		return userDao.findUserByUsernameAndPassword(user.getUsername(), passwd);
	}

	@Override
	public void editPassword(String id, String password) {
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editPassword", password, id);

	}

}
