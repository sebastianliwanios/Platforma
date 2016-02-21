package com.sebastian.platforma.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sebastian.platforma.datamodel.ZlecenieModel;
import com.sebastian.platforma.domain.Zlecenie;

@ManagedBean
@ViewScoped
public class ZleceniaListaTestController {

	private static final Logger logger=LoggerFactory.getLogger(ZleceniaListaTestController.class);
	
	private LazyDataModel<Zlecenie> data=new ZlecenieModel();
	
	@PostConstruct
	public void init(){
		logger.debug("init");
	}

	public LazyDataModel<Zlecenie> getData() {
		logger.debug("GET ");
		return data;
	}
	
	
}
