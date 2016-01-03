package com.sebastian.platforma.services;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sebastian.platforma.controllers.JSFUtility;
import com.sebastian.platforma.dao.IZlecenieDAO;
import com.sebastian.platforma.domain.Dokumentacja;
import com.sebastian.platforma.domain.Zlecenie;

@Service
public class ZlecenieServiceImpl extends AbstractCRUDService<Zlecenie, Integer> implements IZlecenieService {
	
	private static final Logger logger=LoggerFactory.getLogger(ZlecenieServiceImpl.class);
	@Autowired
	private IZlecenieDAO zlecRepo;
	
	@Transactional
	@Override
	public Zlecenie utworz(Zlecenie zlecenie) throws ServiceException {
		logger.debug("Utworz zlecenie");
		//zmienic sciezke na podstawie numer zlecenia itp
		
		 // sciezka: C:\Platforma\miesiac+rok\zleceniodawca\nrzlecenia_ubezpieczyciel_alias
	
		//zlecRepo.findZlecenieByNumerZleceniaAndZleceniodawca(numerZlecenia, zleceniodawca)
		
		if (zlecRepo.findByNumerZlecenia(zlecenie.getNumerZlecenia()).isEmpty()) {
			logger.debug("Nie odnaleziono tego samego numeru zlecenia");
			}
		else {
			throw new ServiceException("zlecenieUnikatowyNumer");
		}
	
		SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
		
		String sciezka = "C:"+File.separator+"Platforma"+File.separator+format.format(zlecenie.getDataOtrzymania())
				+File.separator+zlecenie.getZleceniodawca().getNazwa()+File.separator+zlecenie.getNumerZlecenia()+"_"
				+zlecenie.getUbezpieczyciel().getNazwa()+"_"+zlecenie.getAlias()+File.separator;
		zlecenie.setSciezka(sciezka);
		
		for (Dokumentacja d:zlecenie.getDokumentacja()) {//d - sciezka-> c:tmp00123.tmp, petla zapisuje wszystkie zaznaczone pliki
			File tymczasowy = new File(d.getSciezka()); // tworzymy sciezke do plikow tymczasowych
			File plikDocelowy = new File(sciezka+d.getNazwa()); // tworzymy sciezke do plikow docelowych
			plikDocelowy.mkdirs(); // tworzymy folder w sciezce docelowej
			try {
				Files.copy(tymczasowy.toPath(), plikDocelowy.toPath(), StandardCopyOption.REPLACE_EXISTING); // kopiujemy pliki z folderu tymczasowego do docelowego
			} catch (IOException e) {
				logger.error("Błąd kopiowania plików", e);
				throw new ServiceException("BladKopiowaniaPlikow");
			}
			boolean resultat = tymczasowy.delete();
			if (resultat == false) {
				logger.warn("Nie udało się usunąć pliku tymczasowego {}", tymczasowy.getPath());
				}
				d.setSciezka(sciezka+d.getNazwa()); // do kazdego dokumentu zapisujemy sciezke, ktora zostala utworzona
			}
		
			return super.utworz(zlecenie);
		}
		
		@Transactional
		@Override
		public void usunPoID(Integer id) {
			
			super.usunPoID(id);
		}

		@Override
		public Zlecenie zapisz(Zlecenie encja) throws ServiceException {
			
			return super.zapisz(encja);
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
