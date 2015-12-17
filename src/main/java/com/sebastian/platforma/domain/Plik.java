package com.sebastian.platforma.domain;

public class Plik {

	private String nazwa;//nazwa pliku zaladowanego przez przegladarke
	private String nazwaTymczasowegoPliku;//nazwa pliku tymczosowego zapisanego w pamieci systemu windows
	
	public Plik(String nazwa, String nazwaTymczasowegoPliku) {
		super();
		this.nazwa = nazwa;
		this.nazwaTymczasowegoPliku = nazwaTymczasowegoPliku;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getNazwaTymczasowegoPliku() {
		return nazwaTymczasowegoPliku;
	}

	public void setNazwaTymczasowegoPliku(String nazwaTymczasowegoPliku) {
		this.nazwaTymczasowegoPliku = nazwaTymczasowegoPliku;
	}
	
	
}
