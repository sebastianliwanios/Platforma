package com.sebastian.platforma.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.services.IZlecenieService;

@ManagedBean(name="zlecenieCreateController")
@ViewScoped
public class ZlecenieCreateController extends AbstractCreateController<Zlecenie, Integer, IZlecenieService> {

	public ZlecenieCreateController() {
		super(IZlecenieService.class);
		
	}

	@Override
	public void initCreate() {
		encja=new Zlecenie();
		
	}

}
