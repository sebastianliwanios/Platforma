package com.sebastian.platforma.controllers;

import java.io.Serializable;
import java.util.List;

import com.sebastian.platforma.services.IBaseService;
import com.sebastian.platforma.services.ICRUDService;

public abstract class AbstractListController<T extends Serializable,K extends Serializable,S extends ICRUDService<T, K>> extends AbstractController {

	private transient S service;
	private Class<S> serviceClass;

	public AbstractListController(Class<S> serviceClass) {
		this.serviceClass = serviceClass;
	}

	public List<T> getList()
	{
		return getService().znajdzWszystkie();
	}
	
	public S getService()
	{
		if(service==null)
			service=JSFUtility.findService(serviceClass);
		return service;
	}
}
