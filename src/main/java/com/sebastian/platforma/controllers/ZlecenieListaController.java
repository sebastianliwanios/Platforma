package com.sebastian.platforma.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.print.attribute.standard.Severity;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sebastian.platforma.domain.Dokumentacja;
import com.sebastian.platforma.domain.Ubezpieczyciel;
import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.domain.Zleceniodawca;
import com.sebastian.platforma.services.IUbezpieczycielService;
import com.sebastian.platforma.services.IZlecenieService;
import com.sebastian.platforma.services.IZleceniodawcaService;

@ManagedBean
@SessionScoped
public class ZlecenieListaController extends AbstractListController<Zlecenie, Integer, IZlecenieService> {

	private static final String RESOURCE_BUNDLE_NAME="zlecenieMsg";
	private static final String allNumerOfZlecenieMsg="allNumerOfZlecenieMsg";
	private static final Logger logger=LoggerFactory.getLogger(ZlecenieListaController.class);
	
	// konstruktor musi byc bezargumentowy
	public ZlecenieListaController() {
		super(Zlecenie.class, IZlecenieService.class, RESOURCE_BUNDLE_NAME);
		
	}
	@Override
	public String initNowy() {

		super.initNowy();
		
		obiekt.setDataOtrzymania(new Date());
		obiekt.setSciezka("C:"+File.separator+"Platforma"+File.separator);
		return null;
	}
	
	

	@Override
	public String initEdycja() {
		
		super.initEdycja();
	
		obiekt.setDataModyfikacji(new Date());
		return null;
	}
	//fileA
	//fileA(1)
	//fileA(2)
	public String zwrocUnikalnaNazwePliku(String nazwaPliku) {
		if (obiekt.getDokumentacja() == null || obiekt.getDokumentacja().isEmpty()) {
			logger.debug("Brak plikow w dokumentacji dodaje nowy {}",nazwaPliku);
			return nazwaPliku;
		}
		boolean nazwaJuzIstnieje = czyNazwaIstniejeNaLiscie(nazwaPliku);
		logger.debug("nazwa pliku {} istnieje na liście",nazwaPliku);
		if (nazwaJuzIstnieje == false) {
			return nazwaPliku;
		}
		
		for (int i=0; ; i++) {
			
			String czescNazwa=null;
			String czescRozszerzenie=null;
			int indeksKropki=nazwaPliku.lastIndexOf(".");
			logger.debug("Indeks kropki = {} w naziwe pliku {}", indeksKropki,nazwaPliku);
			czescNazwa = nazwaPliku.substring(0,indeksKropki);
			czescRozszerzenie = nazwaPliku.substring(indeksKropki);
			
			logger.debug("CzęśćNazwa = {}, rozszerzenie = {}", czescNazwa, czescRozszerzenie);
			
			//String[] nazwy = nazwaPliku.split("\\."); // alternatywnie Pattern.quote(".")
			//String nowaNazwa = nazwy[0].concat(+i+"."+nazwy[1]);
			String nowaNazwa = czescNazwa+"("+i+")"+czescRozszerzenie;
			logger.debug("Nowa nazwa dodanego pliku: {}", nowaNazwa);
			nazwaJuzIstnieje = czyNazwaIstniejeNaLiscie(nowaNazwa);
			if (nazwaJuzIstnieje == false) {
				return nowaNazwa;
			}
			
		}
		
	}
	
	private boolean czyNazwaIstniejeNaLiscie(String nowaNazwa) {
		for(Dokumentacja d: obiekt.getDokumentacja()) {
			if(d.getNazwa().equals(nowaNazwa)) {
				logger.debug("Plik o naziwe {} juz istnieje",nowaNazwa);
				return true;
			}
		}
		logger.debug("Brak pliku o nazwie {}",nowaNazwa);
		return false;
	}
	
	public synchronized void  handleFileUpload(FileUploadEvent event) 
	{
		//Dokumentacja
		logger.debug("Pobieram plik "+event.getFile().getFileName());
		try
		{
			File tempFile=File.createTempFile("platforma", ".tmp");
			logger.trace("Utworzono plik tymczasowy {}",tempFile.getPath());
			InputStream input=event.getFile().getInputstream();  // zaznaczamy pliki do skopiowania do sciezki tymczasowej
			try
			{
				Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING); // kopiujemy do sciezki tymczasowej
			}
			finally{
				input.close();
			}
			logger.trace("Skopiowano dane z pliku upload do pliku tymczasowego");
			
			Dokumentacja dokumentacja=new Dokumentacja();
			dokumentacja.setNazwa(zwrocUnikalnaNazwePliku(event.getFile().getFileName()));
			dokumentacja.setSciezka(tempFile.getAbsolutePath());
			dokumentacja.setTymczasowy(true);
			dokumentacja.setDataDodania(new Date());
			this.obiekt.dodajDokument(dokumentacja);
		}
		catch(IOException e)
		{
			logger.error("Blad tworzenia pliku tymczasowego",e);
		}
		
		System.out.println(event.getFile().getFileName());
	}
	
	public void usunDokument(String nazwaPliku)
	{
		Iterator<Dokumentacja> iter=this.obiekt.getDokumentacja().iterator();
		while(iter.hasNext())
		{
			Dokumentacja dok=iter.next();
			if(dok.getNazwa().equals(nazwaPliku))
			{
				iter.remove();
				break;
			}
		}
		
	}
	
	public List<Ubezpieczyciel> getUbezpieczycieleLista() {
		return JSFUtility.findService(IUbezpieczycielService.class).znajdzWszystkie();
	}
	
	public List<Zleceniodawca> getZleceniodawcaLista() {
		return JSFUtility.findService(IZleceniodawcaService.class).znajdzWszystkie();
	}
	
	
	@Override
	public String remove() {
		
		return super.remove();
	}
	
	
	
	
}
