package com.sebastian.platforma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sebastian.platforma.dao.IZlecenieDAO;
import com.sebastian.platforma.domain.Zlecenie;

@Service
@Transactional
public class ZlecenieServiceImpl implements IZlecenieService {

	@Autowired
	private IZlecenieDAO dao;
	
	@Override
	public Zlecenie dodajZlecenie(Zlecenie zlecenie) {
		return dao.save(zlecenie);
	}
	
	@Override
	public void usunZlecenie(String numer) {
		dao.delete(numer);
	}
}
