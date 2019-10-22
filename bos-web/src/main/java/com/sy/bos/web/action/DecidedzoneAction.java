package com.sy.bos.web.action;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sy.bos.domain.Decidedzone;
import com.sy.bos.service.DecidedzoneService;
import com.sy.bos.utils.PageBean;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {

	private String[] subareaid;

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	@Autowired
	private DecidedzoneService decidedZoneService;

	public String add() throws Exception {
		decidedZoneService.save(model, subareaid);
		return "list";
	}

	private int page;
	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String pageQuery() throws Exception {
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Decidedzone.class);
		pageBean.setDetachedCriteria(detachedCriteria);
		decidedZoneService.pageQuery(pageBean);
		// 使用json-lib将pageBean转为json格式
		JsonConfig jConfig = new JsonConfig();
		jConfig.setExcludes(new String[] { "currentPage", "detachedCriteria", "pageSize", "decidedzones", "subareas" });
		String json = JSONObject.fromObject(pageBean, jConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return "none";
	}

}
