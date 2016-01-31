package com.sebastian.platforma.controllers;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sebastian.platforma.services.ServiceException;
/**
 * Kontroler bazowy dla innych kontrolerów JSF oraz kontrolerow abstrakcyjnych, zawiera metody dla wszytskich kontrolerow
 * @author Sebastian
 *
 */
public abstract class AbstractController {

	private static final Logger logger=LoggerFactory.getLogger(AbstractController.class);
	
	protected void handleMessage(ServiceException e)
	{
		String errorCode=e.getErrorCode();
		ResourceBundle bundle=JSFUtility.getResourceBundle("serviceMessage");
		if(bundle==null)
			logger.error("Service Bundle not found");
		
		String message=bundle.getString(errorCode);
		if(message==null)
		{
			logger.error("Brak wiadomosci dla klucza {}",errorCode);
			message="Brak wiadomości dla klucza "+errorCode;
		}
		
		JSFUtility.addGlobalMessage(FacesMessage.SEVERITY_ERROR,message );
	}
}
