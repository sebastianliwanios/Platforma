package com.sebastian.platforma.controllers;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.sebastian.platforma.domain.Ubezpieczyciel;
import com.sebastian.platforma.services.IUbezpieczycielService;

@FacesConverter("UbezpieczycielConverter")
public class UbezpieczycielConverter implements Converter {

	/**
	 * metoda zmienia teskt na obiekt
	 */
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
			if (value == null || value.length()==0)
			{
				return null;
			}
			Short id = Short.parseShort(value);
			return JSFUtility.findService(IUbezpieczycielService.class).zanjdzPoID(id);
		
	}

	/**
	 * metoda zamienia obiekt na tekst - zamienia liczbę na String
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
			if (value == null) {
				return "";
			}
		Ubezpieczyciel ubezpieczyciel = (Ubezpieczyciel) value;
		
		return String.valueOf(ubezpieczyciel.getId());
	}

	
}
