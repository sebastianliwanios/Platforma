package com.sebastian.platforma.controllers;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.sebastian.platforma.domain.Zleceniodawca;
import com.sebastian.platforma.services.IZleceniodawcaService;

@FacesConverter("ZleceniodawcaConverter")
public class ZleceniodawcaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		if (value==null || value.length()==0) {
			return null;
		}
		Short id = Short.parseShort(value);
		return JSFUtility.findService(IZleceniodawcaService.class).zanjdzPoID(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		if (value == null) {
			return "";
		}
		Zleceniodawca zleceniodawca = (Zleceniodawca) value;
		return String.valueOf(zleceniodawca.getId());
	}

}
