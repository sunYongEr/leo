package com.sy.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sy.bos.domain.Region;
import com.sy.bos.service.RegionService;
import com.sy.bos.utils.PageBean;
import com.sy.bos.utils.PinYin4jUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {

	@Autowired
	private RegionService regionService;
	private File regionFile;

	public String importFile() throws Exception {

		List<Region> regionList = new ArrayList<Region>();
		HSSFWorkbook file = new HSSFWorkbook(new FileInputStream(regionFile));
		HSSFSheet sheetAt = file.getSheetAt(0);
		for (Row row : sheetAt) {
			int rowNum = row.getRowNum();
			if (rowNum == 0) {
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			Region region = new Region(id, province, city, district, postcode, null, null, null);

			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);
			String info = province + city + district;
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			// 简码
			String shortcode = StringUtils.join(headByString);
			// 城市编码
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			region.setShortcode(shortcode);
			region.setCitycode(citycode);

			regionList.add(region);
		}
		regionService.save(regionList);
		return NONE;

	}

	private int page;
	private int rows;

	public String pageQuery() throws Exception {
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Region.class);
		pageBean.setDetachedCriteria(detachedCriteria);
		regionService.pageQuery(pageBean);
		// 排除不需要的字段
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "subareas" });
		// 将jsonConfig传入到fromObject（）中
		String json = JSONObject.fromObject(pageBean, jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return "none";
	}

	// 区域下拉框数据
	private String q;

	public String listAjax() throws Exception {
		List<Region> list = null;
		if (StringUtils.isNoneBlank(q)) {
			list = regionService.findListByQ(q);
		} else {
			list = regionService.findAll();
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "currentPage", "detachedCriteria", "pageSize", "subareas" });
		String jsonList = JSONArray.fromObject(list, jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(jsonList);
		return "none";
	}

	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
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

	public void setQ(String q) {
		this.q = q;
	}

}
