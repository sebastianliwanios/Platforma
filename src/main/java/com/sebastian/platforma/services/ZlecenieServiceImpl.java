package com.sebastian.platforma.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.sebastian.platforma.domain.Zlecenie;

@Service
@Transactional
public class ZlecenieServiceImpl extends AbstractCRUDService<Zlecenie, Integer> implements IZlecenieService {

	@Override
	public void preUtworz() throws ServiceException {
		// TODO Auto-generated method stub
		throw new ServiceException("zlecenieUnikatowyNumer");
	}

	@Override
	public void preZapisz() throws ServiceException {
		// TODO Auto-generated method stub
		
	}
}
