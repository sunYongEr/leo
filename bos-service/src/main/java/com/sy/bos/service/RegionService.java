package com.sy.bos.service;

import java.util.List;

import com.sy.bos.domain.Region;
import com.sy.bos.utils.PageBean;

public interface RegionService {

	void save(List<Region> regionList);

	void pageQuery(PageBean pageBean);

	List<Region> findAll();

	List<Region> findListByQ(String q);

}
