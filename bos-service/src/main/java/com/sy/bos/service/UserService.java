package com.sy.bos.service;

import com.sy.bos.domain.User;

public interface UserService {

	User login(User model);

	void editPassword(String id, String password);

}
