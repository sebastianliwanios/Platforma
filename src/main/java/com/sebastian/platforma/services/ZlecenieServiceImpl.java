package com.sebastian.platforma.services;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sebastian.platforma.controllers.ZlecenieListaController;
import com.sebastian.platforma.domain.Dokumentacja;
import com.sebastian.platforma.domain.Zlecenie;

@Service
@Transactional
public class ZlecenieServiceImpl extends AbstractCRUDService<Zlecenie, Integer> implements IZlecenieService {
	
	private static final Logger logger=LoggerFactory.getLogger(ZlecenieServiceImpl.class);
	
	
	@Override
	public Zlecenie utworz(Zlecenie zlecenie) throws ServiceException {
		logger.debug("Utworz zlecenie");
		//zmienic sciezke na podstawie numer zlecenia itp
		
		 // sciezka: C:\Platforma\miesiac+rok\zleceniodawca\nrzlecenia_ubezpieczyciel_alias
		
		
		//!!!!!! Sprawdzic czy numer i zleceniodawce zlecenia juz istnieja jak tak to wyrzucic wyjatek
		//Pobieramy liste zlecen po numerze zlecenia ktore chcemy dodac
		//sprawdzamy czy lista jest pusta jak tak to ok jak nie to wyrzucamy wyjatek
		//List<Zlecenie> findZlecenieByNumerAndByZlecenieNumer
		// if (findZlecenieByNumerAndByZlecenieNumer.isEmpty) ok
		ZlecenieRepository zlecRepo;
		//zlecRepo.findZlecenieByNumerZleceniaAndZleceniodawca(numerZlecenia, zleceniodawca)
		
		SimpleDateFormat format = new SimpleDateFormat("MM yy");
		
		String sciezka = "C:"+File.separator+"Platforma"+File.separator+format.format(zlecenie.getDataOtrzymania())
				+File.separator+zlecenie.getZleceniodawca().getNazwa()+File.separator+zlecenie.getNumerZlecenia()+"_"
				+zlecenie.getUbezpieczyciel().getNazwa()+"_"+zlecenie.getAlias()+File.separator;
		zlecenie.setSciezka(sciezka);
		
		for (Dokumentacja d:zlecenie.getDokumentacja()) {//d - sciezka-> c:tmp00123.tmp
			File tymczasowy = new File(d.getSciezka());
			File plikDocelowy = new File(sciezka+d.getNazwa());
			plikDocelowy.mkdirs();
			try {
				Files.copy(tymczasowy.toPath(), plikDocelowy.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				logger.error("Błąd kopiowania plików", e);
				throw new ServiceException("BladKopiowaniaPlikow");
			}
			boolean resultat = tymczasowy.delete();
			if (resultat == false) {
				logger.warn("Nie udało się usunąć pliku tymczasowego {}", tymczasowy.getPath());
			}
			d.setSciezka(sciezka+d.getNazwa());
		}
		
	
		
		
		//pobieramy pliki tymczasowe 
		//1. Tworzymy pliki stale w sciezce zlecenia(kopiowanie plikow tymczasowych do plikow glownych)
		//2. Tworzymy dla kazdego pliku obiekt klasy Dokumentacja
		//3. 
		//Files.copy(output,input)-> output plik docelowy, input plik tymczasowy
		
		return super.utworz(zlecenie);
	}
	
	
	/*
	@Override
	public void preUtworz() throws ServiceException {
		throw new ServiceException("zlecenieUnikatowyNumer");
	}
	*/

	/*@Override
	public Zlecenie zapisz(Zlecenie encja) throws ServiceException {
		
		//sprawdzenie numeru w bazie
		//wyslanie maila
		Zlecenie z=dao.save(encja);
		//wyslanie maila
		return z;
	}*/


	/*
	@Override
	public void preZapisz() throws ServiceException {
		
	}
	*/
	
	
}
