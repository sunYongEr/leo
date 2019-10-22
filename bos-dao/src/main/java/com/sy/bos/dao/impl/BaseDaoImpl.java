package com.sy.bos.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.sy.bos.dao.BaseDao;
import com.sy.bos.utils.PageBean;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	public Class<T> entityClass;

	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public BaseDaoImpl() {
		ParameterizedType Superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		entityClass = (Class<T>) Superclass.getActualTypeArguments()[0];
	}

	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);

	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);

	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);

	}

	@Override
	public T findById(Serializable id) {

		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		String hql = "FROM " + entityClass.getSimpleName();

		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	@Override
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			query.setParameter(i++, object);
		}
		query.executeUpdate();
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();

		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> totalList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long total = totalList.get(0);
		pageBean.setTotal(total.intValue());
		detachedCriteria.setProjection(null);
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	@Override
	public void saveOrupdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);

	}

	@Override
	public List<T> findByCriteria(DetachedCriteria dCriteria) {

		return (List<T>) this.getHibernateTemplate().findByCriteria(dCriteria);
	}

}
