package com.sy.bos.dao;

import com.sy.bos.domain.User;

public interface UserDao extends BaseDao<User> {

	User findUserByUsernameAndPassword(String username, String passwd);

}
