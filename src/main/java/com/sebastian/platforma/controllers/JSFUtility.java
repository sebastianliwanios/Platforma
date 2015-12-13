package com.sebastian.platforma.controllers;

import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
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
	
	public static void addGlobalMessage(FacesMessage.Severity severity,String message)
	{
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message,message ));
	}
	
	public static ResourceBundle getResourceBundle(String var)
	{
		//FacesContext.getCurrentInstance().getApplication().ev
		// odwoluje sie do danego pliku zarejestrowanego w faces-config.xml (resourceBundle)
		FacesContext context=FacesContext.getCurrentInstance();
		Application application=context.getApplication();
		
		return (ResourceBundle)application.getResourceBundle(context, var);
	}
	//#{session}
	public static Object getExpression(String expression)
	{
		
		FacesContext context = FacesContext.getCurrentInstance();
		StringBuilder builder=new StringBuilder();
		builder.append("#");
		builder.append("{");
		builder.append(expression);
		builder.append("}");
		
		return context.getApplication().evaluateExpressionGet(context, builder.toString(), Object.class);
	}
}
