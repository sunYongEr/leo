package com.sy.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sy.bos.dao.SubareaDao;
import com.sy.bos.domain.Subarea;
import com.sy.bos.service.SubareaService;
import com.sy.bos.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {

	@Autowired
	private SubareaDao subareaDao;

	@Override
	public void save(Subarea model) {
		subareaDao.save(model);

	}

	@Override
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);

	}

	@Override
	public List<Subarea> findAll() {

		return subareaDao.findAll();
	}

	@Override
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Subarea.class);
		dCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(dCriteria);
	}
}
