package com.sebastian.platforma.services;

import com.sebastian.platforma.domain.Dokumentacja;
import com.sebastian.platforma.domain.Zlecenie;

public interface IZlecenieService extends ICRUDService<Zlecenie, Integer> {
	
	public Dokumentacja znajdzDokument(Long id);
}
