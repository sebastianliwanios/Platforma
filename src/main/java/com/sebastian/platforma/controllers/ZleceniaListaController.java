package com.sebastian.platforma.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.services.IZlecenieService;

@ManagedBean
@ViewScoped
public class ZleceniaListaController extends AbstractListController<Zlecenie, Integer, IZlecenieService> {

	public ZleceniaListaController() {
		super(IZlecenieService.class);
	
	}

	
}
