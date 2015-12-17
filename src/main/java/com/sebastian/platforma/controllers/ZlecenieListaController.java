package com.sebastian.platforma.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.print.attribute.standard.Severity;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sebastian.platforma.domain.Dokumentacja;
import com.sebastian.platforma.domain.Ubezpieczyciel;
import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.domain.Zleceniodawca;
import com.sebastian.platforma.services.IUbezpieczycielService;
import com.sebastian.platforma.services.IZlecenieService;
import com.sebastian.platforma.services.IZleceniodawcaService;

@ManagedBean
@ViewScoped
public class ZlecenieListaController extends AbstractListController<Zlecenie, Integer, IZlecenieService> {

	private static final String RESOURCE_BUNDLE_NAME="zlecenieMsg";
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
	//fileA
	//fileA(1)
	//fileA(2)
	public String zwrocUnikalnaNazwePliku(String nazwaPliku) {
		if (obiekt.getDokumentacja() == null || obiekt.getDokumentacja().isEmpty()) {
			return nazwaPliku;
		}
		boolean nazwaJuzIstnieje = czyNazwaIstniejeNaLiscie(nazwaPliku);
		
		if (nazwaJuzIstnieje == false) {
			return nazwaPliku;
		}
		for (int i=1; ; i++) {
			String nowaNazwa = nazwaPliku+"("+i+")"; // + rozszerzenie
			nazwaJuzIstnieje = czyNazwaIstniejeNaLiscie(nowaNazwa);
			if (nazwaJuzIstnieje == false) {
				return nowaNazwa;
			}
			
		}
		
	}
	
	private boolean czyNazwaIstniejeNaLiscie(String nowaNazwa) {
		for(Dokumentacja d: obiekt.getDokumentacja()) {
			if(d.getNazwa().equals(nowaNazwa)) {
				return true;
			}
		}
		return false;
	}
	
	public void handleFileUpload(FileUploadEvent event) 
	{
		//Dokumentacja
		logger.debug("Pobieram plik "+event.getFile().getFileName());
		try
		{
			File tempFile=File.createTempFile("platforma", ".tmp");
			logger.trace("Utworzono plik tymczasowy {}",tempFile.getPath());
			InputStream input=event.getFile().getInputstream();
			try
			{
				Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
	
}
