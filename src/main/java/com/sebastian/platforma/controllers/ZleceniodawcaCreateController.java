package com.sebastian.platforma.controllers;

import com.sebastian.platforma.domain.Zleceniodawca;
import com.sebastian.platforma.services.IZleceniodawcaService;

public class ZleceniodawcaCreateController extends AbstractCreateController<Zleceniodawca, Short, IZleceniodawcaService>{

	public ZleceniodawcaCreateController() {
		super(IZleceniodawcaService.class);
		
	}

	@Override
	public void initCreate() {
		encja= new Zleceniodawca();
		
	}

}
