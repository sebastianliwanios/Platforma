package com.sebastian.platforma.services;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractCRUDService<T extends Serializable,K extends Serializable> extends AbstractBaseService<T, K> implements ICRUDService<T, K>
{

	@Override
	@Transactional
	public T utworz(T encja) throws ServiceException {
		
		//preUtworz();
		return dao.save(encja);
	}
	
	//public abstract void preUtworz() throws ServiceException;
	//public abstract void preZapisz() throws ServiceException;

	@Override
	@Transactional
	public T zapisz(T encja) throws ServiceException {
		
		//preZapisz();
		return dao.save(encja);
	}

	@Override
	@Transactional(readOnly=false)
	public void usun(T encja) {
		
		dao.delete(encja);
	}

	@Override
	@Transactional(readOnly=false)
	public void usunPoID(K id) {
		
		dao.delete(id);
	}

}
