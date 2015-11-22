package com.sebastian.platforma.controllers;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sebastian.platforma.domain.Zleceniodawca;
import com.sebastian.platforma.services.IZleceniodawcaService;
import com.sebastian.platforma.services.ServiceException;

@ManagedBean
@ViewScoped
public class ZleceniodawcaListaController extends AbstractListController<Zleceniodawca, Short, IZleceniodawcaService>{

	private Logger logger=LoggerFactory.getLogger(ZleceniodawcaListaController.class);
	
	private Zleceniodawca obiekt;
	private boolean sukces;
	private int tryb;
	
	private static final int TRYB_DODAWANIE=0;
	private static final int TRYB_EDYCJA=1;
	private static final int TRYB_WYSWIETLANIE=2;
	
	public ZleceniodawcaListaController()
	{
		super(IZleceniodawcaService.class); //rodzica
		
	}
	
	public String initNowy()
	{
		tryb=TRYB_DODAWANIE;
		logger.debug("initNowy");
		obiekt=new Zleceniodawca();// T obiekt=new T(); public abstract createInstance(){return new Zleceniodawca()}
		sukces=false;              // T obiekt=createInstance();
		return null;
	}
	
	public String initEdycja()
	{
		tryb=TRYB_EDYCJA;
		sukces=false;
		return null;
	}
	
	public String initWyswietl()
	{
		tryb=TRYB_WYSWIETLANIE;
		sukces=false;
		return null;
	}
	
	public String create()
	{
		logger.debug("Create");
		try {
			
			getService().utworz(obiekt);
			sukces=true;
			obiekt=null;
			JSFUtility.addGlobalMessage(FacesMessage.SEVERITY_INFO,"Zleceniodawca dodany pomyślnie!");
		} catch (ServiceException e) {
			handleMessage(e);
		}
		
		return null;
	}
	
	public String save()
	{
		try {
			getService().zapisz(obiekt);
			sukces=true;
			obiekt=null;
			JSFUtility.addGlobalMessage(FacesMessage.SEVERITY_INFO,"Zapisano iformacje pomyślnie!");
		} catch (ServiceException e) {
			handleMessage(e);
			//e.printStackTrace();
		}
		return null;
	}
	
	public String remove()
	{
		getService().usun(obiekt);
		JSFUtility.addGlobalMessage(FacesMessage.SEVERITY_INFO, "Usunięto wybranego zleceniodawcę");
		return null;
		
	}

	public Zleceniodawca getObiekt() {
		return obiekt;
	}

	public void setObiekt(Zleceniodawca obiekt) {
		this.obiekt = obiekt;
	}

	public boolean isSukces() {
		return sukces;
	}

	public void setSukces(boolean sukces) {
		this.sukces = sukces;
	}
	
	public boolean isDodawanie()
	{
		return tryb==TRYB_DODAWANIE;
	}
	
	public boolean isEdycja()
	{
		return tryb==TRYB_EDYCJA;
	}
	
	public boolean isWyswietlanie()
	{
		return tryb==TRYB_WYSWIETLANIE;
	}

}
