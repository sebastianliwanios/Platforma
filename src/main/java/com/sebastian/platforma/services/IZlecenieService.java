package com.sebastian.platforma.services;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sebastian.platforma.domain.Dokumentacja;
import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.domain.filters.GenericFilter;

public interface IZlecenieService extends ICRUDService<Zlecenie, Integer> {
	
	public Dokumentacja znajdzDokument(Long id);

	public Page<Zlecenie> filtrujZlecenia(PageRequest stronicowanie,GenericFilter filter);
}
