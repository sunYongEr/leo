package com.sy.bos.service;

import com.sy.bos.domain.Decidedzone;
import com.sy.bos.utils.PageBean;

public interface DecidedzoneService {

	void save(Decidedzone model, String[] subareaid);

	void pageQuery(PageBean pageBean);

}
