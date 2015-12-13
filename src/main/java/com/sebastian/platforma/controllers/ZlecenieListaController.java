package com.sebastian.platforma.controllers;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.print.attribute.standard.Severity;

import org.primefaces.model.UploadedFile;

import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.services.IZlecenieService;

@ManagedBean
@ViewScoped
public class ZlecenieListaController extends AbstractListController<Zlecenie, Integer, IZlecenieService> {

	private static final String RESOURCE_BUNDLE_NAME="zlecenieMsg";
	private UploadedFile file;
	
	// konstruktor musi byc bezargumentowy
	public ZlecenieListaController() {
		super(Zlecenie.class, IZlecenieService.class, RESOURCE_BUNDLE_NAME);
		
	}
	@Override
	public String initNowy() {
		
		super.initNowy();
		obiekt.setDataOtrzymania(new Date());
		return null;
	}
	
	/*public void upload() {
		if (file != null){
			FacesMessage massage = new FacesMessage("Plik " +file.getFileName()+ "załącznono");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
	}*/
	
}
