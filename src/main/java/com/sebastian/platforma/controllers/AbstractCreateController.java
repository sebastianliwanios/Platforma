package com.sebastian.platforma.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import com.sebastian.platforma.services.ICRUDService;
import com.sebastian.platforma.services.ServiceException;

public abstract class AbstractCreateController<T extends Serializable,K extends Serializable,S extends ICRUDService<T, K>> extends AbstractController {

	protected T encja;
	private transient S service;
	private Class<S> serviceClass;
	
	public AbstractCreateController(Class<S> serviceClass) {
		this.serviceClass=serviceClass;
	}
	
	@PostConstruct
	public void init()
	{
		initCreate();
	}
	
	public abstract void initCreate();
	
	public String create()
	{
		try {
			getService().utworz(encja);
			initCreate();
		} catch (ServiceException e) {
			handleMessage(e);
		}
		
		return null;
	}

	public T getEncja() {
		return encja;
	}

	public void setEncja(T encja) {
		this.encja = encja;
	}
	
	public S getService()
	{
		if(service==null)
			service=JSFUtility.findService(serviceClass);
		return service;
	}
}
