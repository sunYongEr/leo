package com.sy.bos.dao;

import java.util.List;

import com.sy.bos.domain.Region;

public interface RegionDao extends BaseDao<Region> {

	List<Region> findListByQ(String q);

}
