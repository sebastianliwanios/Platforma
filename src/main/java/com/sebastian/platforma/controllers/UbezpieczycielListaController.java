package com.sebastian.platforma.controllers;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sebastian.platforma.domain.Ubezpieczyciel;
import com.sebastian.platforma.services.IUbezpieczycielService;

@ManagedBean
@ViewScoped
public class UbezpieczycielListaController extends AbstractListController<Ubezpieczyciel, Short, IUbezpieczycielService>{

	//private final static Logger logger = LoggerFactory.getLogger(UbezpieczycielListaController.class); 

	private static final String RESOURCE_BUNDLE_NAME="ubezpieczycielMsg";
	
	public UbezpieczycielListaController() {
		super(Ubezpieczyciel.class,IUbezpieczycielService.class,RESOURCE_BUNDLE_NAME);
		
	}

	


	
}
