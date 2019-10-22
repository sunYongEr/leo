package com.sy.bos.web.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sy.bos.domain.Staff;
import com.sy.bos.service.StaffService;
import com.sy.bos.utils.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {

	@Autowired
	private StaffService staffService;

	public String add() throws Exception {
		staffService.save(model);
		return "list";
	}

	// 属性驱动 ，接收页面提交的参数
	private int page;
	private int rows;

	public String pageQuery() throws Exception {

		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		pageBean.setDetachedCriteria(detachedCriteria);
		staffService.pageQuery(pageBean);
		// 使用json-lib将pageBean转为json格式
		JsonConfig jConfig = new JsonConfig();
		jConfig.setExcludes(new String[] { "decidedzones" });
		String json = JSONObject.fromObject(pageBean).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return NONE;
	}

	private String ids;

	public String delete() throws Exception {
		staffService.delete(ids);

		return "list";
	}

	public String edit() throws Exception {
		Staff staff = staffService.findById(model.getId());
		staff.setName(model.getName());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staff.setTelephone(model.getTelephone());
		staffService.update(staff);
		return "list";
	}

	public String listAjax() throws Exception {
		List<Staff> list = staffService.findListNotDelete();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "decidedzones" });
		String json = JSONArray.fromObject(list, jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return "none";
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
