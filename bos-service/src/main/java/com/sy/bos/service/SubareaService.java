package com.sy.bos.service;

import java.util.List;

import com.sy.bos.domain.Subarea;
import com.sy.bos.utils.PageBean;

public interface SubareaService {

	void save(Subarea model);

	void pageQuery(PageBean pageBean);

	List<Subarea> findAll();

	List<Subarea> findListNotAssociation();

}
