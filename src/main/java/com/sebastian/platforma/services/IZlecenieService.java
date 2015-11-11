package com.sebastian.platforma.services;

import com.sebastian.platforma.domain.Zlecenie;

public interface IZlecenieService {
	
	public Zlecenie dodajZlecenie(Zlecenie zlecenie);
	public void usunZlecenie(String numer);
}
