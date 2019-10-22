package com.sy.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sy.bos.dao.UserDao;
import com.sy.bos.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User findUserByUsernameAndPassword(String username, String passwd) {
		String hql = "from User u where u.username=? and u.password=? ";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username, passwd);

		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

}
