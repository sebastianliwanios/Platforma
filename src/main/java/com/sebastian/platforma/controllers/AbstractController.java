package com.sebastian.platforma.controllers;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;



import com.sebastian.platforma.services.ServiceException;
/**
 * Kontroler bazowy dla innych kontrolerów JSF oraz kontrolerow abstrakcyjnych, zawiera metody dla wszytskich kontrolerow
 * @author Sebastian
 *
 */
public abstract class AbstractController {

	protected void handleMessage(ServiceException e)
	{
		String errorCode=e.getErrorCode();
		ResourceBundle bundle=JSFUtility.getResourceBundle("serviceMessage");
		JSFUtility.addGlobalMessage(FacesMessage.SEVERITY_ERROR, bundle.getString(errorCode));
	}
}
