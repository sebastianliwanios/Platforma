package com.sebastian.platforma.domain;


import java.util.HashSet;
import java.util.Set;

public class WiadomoscMail {

	private String tytul;
	private String tekst;
	private Set<String> odbiorcy;
	private Set<String> odbiorcyWkopii;
	private Set<PlikMail> pliki;
	
	public WiadomoscMail() {
		super();
		odbiorcy=new HashSet<String>();
		odbiorcyWkopii=new HashSet<String>();
		pliki=new HashSet<PlikMail>();
	}


	public WiadomoscMail(String tytul, String tekst) {
		this();
		this.tytul = tytul;
		this.tekst = tekst;
	}
	
	public boolean dodajOdbiorce(String odbiorca)
	{
		return odbiorcy.add(odbiorca);
	}
	
	public boolean dodajOdbiorceWKopii(String odbiorcaWkopii)
	{
		return odbiorcyWkopii.add(odbiorcaWkopii);
	}
	
	public boolean dodajPliki(String nazwa,String sciezka)
	{
		return pliki.add(new PlikMail(sciezka,nazwa));
	}
	public String getTytul() {
		return tytul;
	}
	public void setTytul(String tytul) {
		this.tytul = tytul;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	public Set<String> getOdbiorcy() {
		return odbiorcy;
	}
	public void setOdbiorcy(Set<String> odbiorcy) {
		this.odbiorcy = odbiorcy;
	}
	public Set<String> getOdbiorcyWkopii() {
		return odbiorcyWkopii;
	}
	public void setOdbiorcyWkopii(Set<String> odbiorcyWkopii) {
		this.odbiorcyWkopii = odbiorcyWkopii;
	}
	public Set<PlikMail> getPliki() {
		return pliki;
	}
	public void setPliki(Set<PlikMail> pliki) {
		this.pliki = pliki;
	}
	
	
}
