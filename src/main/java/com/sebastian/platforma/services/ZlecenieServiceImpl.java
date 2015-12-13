package com.sebastian.platforma.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.sebastian.platforma.domain.Zlecenie;

@Service
@Transactional
public class ZlecenieServiceImpl extends AbstractCRUDService<Zlecenie, Integer> implements IZlecenieService {
	/*
	@Override
	public void preUtworz() throws ServiceException {
		throw new ServiceException("zlecenieUnikatowyNumer");
	}
	*/

	/*@Override
	public Zlecenie zapisz(Zlecenie encja) throws ServiceException {
		
		//sprawdzenie numeru w bazie
		//wyslanie maila
		Zlecenie z=dao.save(encja);
		//wyslanie maila
		return z;
	}*/


	/*
	@Override
	public void preZapisz() throws ServiceException {
		
	}
	*/
}
