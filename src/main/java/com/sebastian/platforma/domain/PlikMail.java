package com.sebastian.platforma.domain;

public class PlikMail {

	private String sciezkaPliku;
	private String nazwaPliku;
	
	public PlikMail(String sciezkaPliku, String nazwaPliku) {
		super();
		this.sciezkaPliku = sciezkaPliku;
		this.nazwaPliku = nazwaPliku;
	}

	public String getSciezkaPliku() {
		return sciezkaPliku;
	}

	public void setSciezkaPliku(String sciezkaPliku) {
		this.sciezkaPliku = sciezkaPliku;
	}

	public String getNazwaPliku() {
		return nazwaPliku;
	}

	public void setNazwaPliku(String nazwaPliku) {
		this.nazwaPliku = nazwaPliku;
	}
	
	
}
