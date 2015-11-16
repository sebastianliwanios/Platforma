package com.sebastian.platforma.services;

import java.io.Serializable;
/**
 * 2gi bazowy - Interfejs ktory powinien byc dziedziczony przez kazdy inny interfejs, ktory ma wykorzystac metody CRUDa
 * @author Sebastian
 *
 * @param <T>
 * @param <K>
 */
public interface ICRUDService<T extends Serializable,K extends Serializable> extends IBaseService<T, K> {

	public T utworz(T encja) throws ServiceException;
	public T zapisz(T encja) throws ServiceException;
	public void usun(T encja);
	public void usunPoID(K id);
	
}
