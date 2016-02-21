package com.sebastian.platforma.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sebastian.platforma.domain.KontoMailowe;
import com.sebastian.platforma.services.IMailService;

@ManagedBean
@ViewScoped
public class KontoMailoweController extends AbstractListController<KontoMailowe, Integer, IMailService>{

	private static final String RESOURCE_BUNDLE_NAME="kontoMailoweMsg";
	
	public KontoMailoweController() {
		super(KontoMailowe.class, IMailService.class, RESOURCE_BUNDLE_NAME);
		
	}

}
