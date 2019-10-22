package com.sy.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sy.bos.dao.StaffDao;
import com.sy.bos.domain.Staff;
import com.sy.bos.service.StaffService;
import com.sy.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffDao staffDao;

	@Override
	public void save(Staff model) {

		staffDao.save(model);

	}

	@Override
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);

	}

	// 将deltag更新：0变1
	@Override
	public void delete(String ids) {
		if (StringUtils.isNoneBlank(ids)) {
			String[] split = ids.split(",");
			for (String id : split) {
				staffDao.executeUpdate("staff.delete", id);
			}
		}

	}

	@Override
	public Staff findById(String id) {
		Staff staff = staffDao.findById(id);
		return staff;
	}

	@Override
	public void update(Staff staff) {
		staffDao.update(staff);

	}

	@Override
	public List<Staff> findListNotDelete() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Staff.class);
		dCriteria.add(Restrictions.eq("deltag", "0"));
		return staffDao.findByCriteria(dCriteria);
	}

}
