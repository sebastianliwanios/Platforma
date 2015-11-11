package com.sebastian.platforma.domain.test;

import java.util.Date;

public class PogodaDekorator extends Pogoda {

	@Override
	public void wypiszNaKonsolePogode() {
		
		System.out.println("Data:"+(new Date()));
		
		super.wypiszNaKonsolePogode();
	}

	public static void main(String[] args)
	{
		Pogoda pogoda=aktualnaPogoda();
		
		pogoda.wypiszNaKonsolePogode();
		
		
	}
	
	public static Pogoda aktualnaPogoda()
	{
		return new PogodaDekorator();
	}
	
}
