package com.sy.bos.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.sy.bos.domain.User;
import com.sy.bos.service.UserService;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	private UserService userService;

	public String login() throws Exception {
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNoneBlank(checkcode) && checkcode.equals(validatecode)) {

			User user = userService.login(model);
			if (user != null) {
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return "home";
			} else {
				addActionError("用户名或密码不正确！");
				return "login";
			}

		} else {

			addActionError("验证码错误！");
			return "login";
		}

	}

	public String logout() throws Exception {
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}

	public String editPassword() throws Exception {
		User user = (User) ActionContext.getContext().getSession().get("loginUser");

		String data = "1";
		try {
			userService.editPassword(user.getId(), model.getPassword());
		} catch (Exception e) {
			data = "0";
			e.printStackTrace();
		}

		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(data);

		return NONE;
	}

}
