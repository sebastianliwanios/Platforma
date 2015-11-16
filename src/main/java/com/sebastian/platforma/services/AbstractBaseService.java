package com.sebastian.platforma.services;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractBaseService<T extends Serializable,K extends Serializable> implements IBaseService<T, K> {

	@Autowired
	protected JpaRepository<T,K> dao;
	
	@Override
	@Transactional(readOnly=true)
	public T zanjdzPoID(K id)
	{
		return dao.findOne(id);
	}
	@Override
	@Transactional(readOnly=true)
	public List<T> znajdzWszystkie()
	{
		return dao.findAll();
	}
	@Override
	@Transactional(readOnly=true)
	public T aktualizuj(T obj)
	{
		return null;
	}
}
