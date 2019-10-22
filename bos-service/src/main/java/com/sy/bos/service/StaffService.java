package com.sy.bos.service;

import java.util.List;

import com.sy.bos.domain.Staff;
import com.sy.bos.utils.PageBean;

public interface StaffService {

	void save(Staff model);

	void pageQuery(PageBean pageBean);

	void delete(String ids);

	Staff findById(String id);

	void update(Staff staff);

	List<Staff> findListNotDelete();

}
