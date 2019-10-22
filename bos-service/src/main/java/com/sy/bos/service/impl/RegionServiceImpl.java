package com.sy.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sy.bos.dao.RegionDao;
import com.sy.bos.domain.Region;
import com.sy.bos.service.RegionService;
import com.sy.bos.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionDao regionDao;

	@Override
	public void save(List<Region> regionList) {
		for (Region region : regionList) {
			regionDao.saveOrupdate(region);
		}

	}

	@Override
	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);

	}

	@Override
	public List<Region> findAll() {
		return regionDao.findAll();

	}

	@Override
	public List<Region> findListByQ(String q) {

		return regionDao.findListByQ(q);

	}

}
