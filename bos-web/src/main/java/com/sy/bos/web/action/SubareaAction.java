package com.sy.bos.web.action;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sy.bos.domain.Region;
import com.sy.bos.domain.Subarea;
import com.sy.bos.service.SubareaService;
import com.sy.bos.utils.FileUtils;
import com.sy.bos.utils.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {

	@Autowired
	private SubareaService subareaService;

	public String add() throws Exception {
		subareaService.save(model);
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
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Subarea.class);
		pageBean.setDetachedCriteria(dCriteria);
		// 条件查询
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		detachedCriteria.createAlias("region", "r");
		String addresskey = model.getAddresskey();
		if (StringUtils.isNotBlank(addresskey)) {
			detachedCriteria.add(Restrictions.ilike("addresskey", "%" + addresskey + "%"));
		}
		Region region = model.getRegion();
		if (region != null) {
			String city = region.getCity();
			if (StringUtils.isNotBlank(city)) {
				detachedCriteria.add(Restrictions.ilike("r.city", "%" + city + "%"));
			}
			String province = region.getProvince();
			if (StringUtils.isNotBlank(province)) {
				detachedCriteria.add(Restrictions.ilike("r.province", "%" + province + "%"));
			}
			String district = region.getDistrict();
			if (StringUtils.isNotBlank(district)) {
				detachedCriteria.add(Restrictions.ilike("r.district", "%" + district + "%"));
			}
		}

		subareaService.pageQuery(pageBean);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig
				.setExcludes(new String[] { "currentPage", "detachedCriteria", "pageSize", "decidedzone", "subareas" });
		String json = JSONObject.fromObject(pageBean, jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);

		return "none";
	}

	// 将数据导出到Excel
	public String exportExcel() throws Exception {
		// 查询出需要导出的数据
		List<Subarea> list = subareaService.findAll();

		// 将数据导入到Excel
		HSSFWorkbook hWorkbook = new HSSFWorkbook();
		HSSFSheet createSheet = hWorkbook.createSheet("分区数据");
		HSSFRow titleRow = createSheet.createRow(0);
		titleRow.createCell(0).setCellValue("分区编号");
		titleRow.createCell(1).setCellValue("开始编号");
		titleRow.createCell(2).setCellValue("结束编号");
		titleRow.createCell(3).setCellValue("位置信息");
		titleRow.createCell(4).setCellValue("省市区");
		// 创建数据表格
		for (Subarea subarea : list) {
			HSSFRow dataRow = createSheet.createRow(createSheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		// 使用输出流进行文件下载
		// 设置导出文件的文件名和类型
		String fileName = "分区数据.xls";
		// 获取用户的浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		// 解决中文文件名乱码问题
		String filename = FileUtils.encodeDownloadFilename(fileName, agent);
		// 获取filename的类型
		String fileType = ServletActionContext.getServletContext().getMimeType(filename);
		// 将文件名返回给客户端
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);

		ServletActionContext.getResponse().setContentType(fileType);

		ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();

		hWorkbook.write(outputStream);

		return "none";
	}

	//
	public String listAjax() throws Exception {
		// 找出没有定区关联的分区数据
		List<Subarea> list = subareaService.findListNotAssociation();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "decidedzone", "region" });
		String json = JSONArray.fromObject(list, jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return "none";
	}

}
