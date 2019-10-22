package com.sy.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sy.bos.dao.RegionDao;
import com.sy.bos.domain.Region;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {

	@Override
	public List<Region> findListByQ(String q) {
		String hql = "from Region r where r.province like ? or r.city like ? or r.district like ? or r.postcode like ? or r.shortcode like ? or r.citycode like ?";
		List<Region> list = (List<Region>) this.getHibernateTemplate().find(hql, "%" + q + "%", "%" + q + "%",
				"%" + q + "%", "%" + q + "%", "%" + q + "%", "%" + q + "%");

		return list;
	}

}
