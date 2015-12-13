package com.sebastian.platforma.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.sebastian.platforma.services.ICRUDService;
import com.sebastian.platforma.services.ServiceException;

public abstract class AbstractListController<T extends Serializable,K extends Serializable,S extends ICRUDService<T, K>> extends AbstractController {

	private static final Logger logger=LoggerFactory.getLogger(AbstractListController.class);
	
	private transient S service;
	protected boolean sukces;
	protected int tryb;
	private Class<S> serviceClass;
	
	protected T obiekt;
	protected Class<T> obiektClass;
	
	public static final int TRYB_DODAWANIE=0;
	public static final int TRYB_EDYCJA=1;
	public static final int TRYB_WYSWIETLANIE=2;
	
	private static final String CREATE_MSG="createMsg";
	private static final String SAVE_MSG="saveMsg";
	private static final String REMOVE_MSG="removeMsg";
	
	private String resourceBundleName;
	
	
// nie mozna przekazac typy T, S i dlatego przekazujemy klase
	
	public AbstractListController(Class<T> obiektClass,Class<S> serviceClass,String resourceBundleName) {
		this.serviceClass = serviceClass;
		this.obiektClass=obiektClass;
		this.resourceBundleName=resourceBundleName;
	}
	
	public String initNowy()
	{
		try
		{
			tryb=TRYB_DODAWANIE;
			//refleksa
			//String.class.newInstance();//newInstace() - tworzy nowa instacje za pomoca konstruktora no-args
			obiekt=obiektClass.newInstance();
			
			sukces=false;             
		}
		 catch (IllegalAccessException e) {
			logger.error("Nie udało sie utworzyć obiektu klasy typu T",e);
		} catch (InstantiationException e) {
			logger.error("Nie udało sie utworzyć obiektu klasy typu T",e);
		}
		return null;
	}
	
	public String initEdycja()
	{
		tryb=TRYB_EDYCJA;
		setSukces(false);
		return null;
	}
	
	public String initWyswietl()
	{
		tryb=TRYB_WYSWIETLANIE;
		setSukces(false);
		return null;
	}
	
	public String create()
	{
		logger.debug("Create");
		try {
			
			getService().utworz(obiekt);
			setSukces(true);
			obiekt=null;
			JSFUtility.addGlobalMessage(FacesMessage.SEVERITY_INFO,JSFUtility.getResourceBundle(resourceBundleName).getString(CREATE_MSG));
		} catch (ServiceException e) {
			handleMessage(e);
		}
		
		return null;
	}
	
	public String save()
	{
		try {
			getService().zapisz(obiekt);
			setSukces(true);
			obiekt=null;
			JSFUtility.addGlobalMessage(FacesMessage.SEVERITY_INFO,JSFUtility.getResourceBundle(resourceBundleName).getString(SAVE_MSG));
		} catch (ServiceException e) {
			handleMessage(e);
			//e.printStackTrace();
		}
		return null;
	}
	
	public String remove()
	{
		getService().usun(obiekt);
		JSFUtility.addGlobalMessage(FacesMessage.SEVERITY_INFO, JSFUtility.getResourceBundle(resourceBundleName).getString(REMOVE_MSG));
		return null;
		
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
	
	public boolean isSukces() {
		return sukces;
	}

	public void setSukces(boolean sukces) {
		this.sukces = sukces;
	}

	public T getObiekt() {
		return obiekt;
	}

	public void setObiekt(T obiekt) {
		this.obiekt = obiekt;
	}
}
