package com.sy.bos.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sy.bos.utils.PageBean;

public interface BaseDao<T> {
	public void save(T entity);

	public void delete(T entity);

	public void update(T entity);

	public void saveOrupdate(T entity);

	public T findById(Serializable id);

	public List<T> findAll();

	public void executeUpdate(String queryName, Object... objects);

	public void pageQuery(PageBean pageBean);

	public List<T> findByCriteria(DetachedCriteria dCriteria);

}