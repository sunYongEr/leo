package com.sy.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sy.bos.dao.DecidedzoneDao;
import com.sy.bos.dao.SubareaDao;
import com.sy.bos.domain.Decidedzone;
import com.sy.bos.domain.Subarea;
import com.sy.bos.service.DecidedzoneService;
import com.sy.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {

	@Autowired
	private DecidedzoneDao decidedzoneDao;
	@Autowired
	private SubareaDao subareaDao;

	@Override
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String subid : subareaid) {
			Subarea subarea = subareaDao.findById(subid);
			subarea.setDecidedzone(model);
		}

	}

	@Override
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);

	}

}
