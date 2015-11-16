package com.sebastian.platforma.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.sebastian.platforma.dao.IZlecenieDAO;
import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.services.IZlecenieService;
import com.sebastian.platforma.services.ServiceException;
import com.sebastian.platforma.services.ZlecenieServiceImpl;

@ManagedBean
@ViewScoped
public class ZlecenieController implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ZlecenieController.class);
	
	private Zlecenie zlecenie;
	//tylko poto aby nie wyszukiwac w kontekscie springa wielokrotnie
	// transient - nie musimy zapamietywac tego obiektu w kontrolerze - poniewaz to jest obiekt biznesowy i zawsze mozna go pobrac
	private transient IZlecenieService zlecenieService;
	
	@PostConstruct
	public void init() {
		logger.debug("Inicjalizacja zlecenia");
		zlecenie = new Zlecenie();
	}
	
	public String createZlecenie() {
		logger.debug("tworzę zlecenie");
		try
		{
			getZlecenieService().utworz(zlecenie);
			zlecenie = new Zlecenie();
		}catch(ServiceException e)
		{
			logger.error("Blad klasy biznesowej",e);
		}
		return null;
	}
	
	private IZlecenieService getZlecenieService()
	{
		if(zlecenieService==null)
			zlecenieService=JSFUtility.findService(IZlecenieService.class);
		
		return zlecenieService;
	}

	public Zlecenie getZlecenie() {
		return zlecenie;
	}

	public void setZlecenie(Zlecenie zlecenie) {
		this.zlecenie = zlecenie;
	}

	
	
	
}
