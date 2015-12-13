package com.sebastian.platforma.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sebastian.platforma.domain.Zleceniodawca;
import com.sebastian.platforma.services.IZleceniodawcaService;

@ManagedBean
@ViewScoped
public class ZleceniodawcaListaController extends AbstractListController<Zleceniodawca, Short, IZleceniodawcaService>{

	//private Logger logger=LoggerFactory.getLogger(ZleceniodawcaListaController.class);
	
	private static final String RESOURCE_BUNDLE_NAME="zleceniodawcaMsg";
	
	public ZleceniodawcaListaController()
	{
		super(Zleceniodawca.class,IZleceniodawcaService.class,RESOURCE_BUNDLE_NAME); //rodzica
		
	}

}
