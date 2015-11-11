package com.sebastian.platforma.controllers;

import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

public class JSFUtility {

	private JSFUtility(){
		
	}
	
	public static<T> T findService(Class<T> clazz)
	{
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		return ctx.getBean(clazz);
		
	}
}
