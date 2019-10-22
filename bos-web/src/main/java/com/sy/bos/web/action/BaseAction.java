package com.sy.bos.web.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected T model;

	@Override
	public T getModel() {

		return model;
	}

	public BaseAction() {
		ParameterizedType Superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = Superclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
