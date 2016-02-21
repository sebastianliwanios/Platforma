package com.sebastian.platforma.services;

import com.sebastian.platforma.domain.KontoMailowe;
import com.sebastian.platforma.domain.WiadomoscMail;

public interface IMailService extends ICRUDService<KontoMailowe, Integer>{
	
	public boolean wyslijMail(WiadomoscMail wiadomosc);
	
}
