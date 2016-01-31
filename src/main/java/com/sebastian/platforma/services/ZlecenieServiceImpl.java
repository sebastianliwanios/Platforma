package com.sebastian.platforma.services;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.application.FacesMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sebastian.platforma.controllers.JSFUtility;
import com.sebastian.platforma.dao.IDokumentacjaDAO;
import com.sebastian.platforma.dao.IZlecenieDAO;
import com.sebastian.platforma.domain.Dokumentacja;
import com.sebastian.platforma.domain.Zlecenie;

@Service
public class ZlecenieServiceImpl extends AbstractCRUDService<Zlecenie, Integer> implements IZlecenieService {
	
	private static final Logger logger=LoggerFactory.getLogger(ZlecenieServiceImpl.class);
	@Autowired
	private IZlecenieDAO zlecRepo;
	
	@Autowired
	private IDokumentacjaDAO dokumentacjaDAO;
	
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
		
		File sciezkaFolder=new File(sciezka);
		boolean rezultat=sciezkaFolder.mkdirs();
		if(!rezultat)
			logger.warn("Nie udało się utworzyć sciezki {}",sciezka);
		zlecenie.setSciezka(sciezka);
		if(zlecenie.getDokumentacja()!=null&&!zlecenie.getDokumentacja().isEmpty())
		{
			for (Dokumentacja d:zlecenie.getDokumentacja()) {//d - sciezka-> c:tmp00123.tmp, petla zapisuje wszystkie zaznaczone pliki
				File tymczasowy = new File(d.getSciezka()); // pobieramy nazwę wszystkich plikow tymczasowych
				File plikDocelowy = new File(sciezka+d.getNazwa()); // tworzymy sciezke do plikow docelowych
				plikDocelowy.mkdirs(); // tworzymy folder wraz z sciezką docelową
				
				try {
					logger.debug("plik tymczasowy to {} oraz docelowy to: {}",tymczasowy, plikDocelowy);
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
	
				String sciezkaBazowaZip = "C:"+File.separator+"Platforma"+File.separator+"ZipFiles"+File.separator+format.format(zlecenie.getDataOtrzymania())
					+File.separator+zlecenie.getZleceniodawca().getNazwa()+File.separator+zlecenie.getNumerZlecenia()+"_"
					+zlecenie.getUbezpieczyciel().getNazwa()+"_"+zlecenie.getAlias();
			
				
				
				String sciezkaZip = sciezkaBazowaZip+"_"+zlecenie.getAlias();
				
				try {
					File fileBazowy = new File(sciezkaBazowaZip);// tworzymy sciezke bazowa w ktorej bedziemy zapisywac pliki
					File file = new File(sciezkaZip+".zip"); // tworzymy folder jak ma sie nazywac gdzie zapisane zostana pliki
					logger.debug("Sciezka file: {}", file.getAbsolutePath());
					fileBazowy.mkdirs();
					BufferedInputStream bis = null;
					BufferedOutputStream bos = null;
					FileOutputStream out = new FileOutputStream(file); // wskazujemy miejsce gdzie maja byc zapisane pliki
					bos = new BufferedOutputStream(out);
					ZipOutputStream zos = new ZipOutputStream(bos);
					byte data[] = new byte[2048];
					
					//File f = new File(sciezka);
					
					for (Dokumentacja d:zlecenie.getDokumentacja()) { // przeszukujemy petlą całą dokumentacje 
						File f = new File(d.getSciezka()); // wskazujemy sciezke gdzie znajduja sie pliki, ktore maja byc skompresowane
						System.out.println(d.getNazwa());
						FileInputStream fi = new FileInputStream(f);
						bis = new BufferedInputStream(fi, 2048);
						ZipEntry entry = new ZipEntry(f.getName());
						zos.putNextEntry(entry);
						
						int length;
						while((length = bis.read(data, 0, 2048)) != -1) {
							zos.write(data, 0, length);
						}
						
						bis.close();
						fi.close();
					}
					zos.close();
					
					logger.debug("pomyślnie zapisano pliki");
				} catch (FileNotFoundException e) {
					logger.debug("Nie odnaleziono pliku ");
					e.printStackTrace();
				} catch (IOException e) {
					logger.debug("Nie zapisano pliku");
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Error unknown",e);
				}
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
			
			List<Zlecenie> lista=zlecRepo.findByNumerZlecenia(encja.getNumerZlecenia());
			if(!lista.isEmpty())
			{
				Zlecenie zlec=lista.get(0);
				if(!zlec.equals(encja))
				{
					logger.debug("Zlecenie o numerze {} juz istnieje w zleceniu o id={}",encja.getNumerZlecenia(),zlec.getId());
					throw new ServiceException("zlecenieUnikatowyNumer");
				}
			}
			if(encja.getDokumentacja()!=null&&!encja.getDokumentacja().isEmpty())
			{
				for (Dokumentacja d:encja.getDokumentacja())
				{
					if(!d.isTymczasowy())
						continue;
					File tymczasowy = new File(d.getSciezka()); // pobieramy sciezke do plikow tymczasowych
					File plikDocelowy = new File(encja.getSciezka()+d.getNazwa()); // tworzymy sciezke do plikow docelowych
					try
					{
						Files.copy(tymczasowy.toPath(), plikDocelowy.toPath(), StandardCopyOption.REPLACE_EXISTING);
					}catch(IOException e)
					{
						logger.error("Błąd kopiowania plików", e);
					}
					
					boolean resultat = tymczasowy.delete();
					if (resultat == false) {
						logger.warn("Nie udało się usunąć pliku tymczasowego {}", tymczasowy.getPath());
					}
					d.setSciezka(encja.getSciezka()+d.getNazwa()); // do kazdego dokumentu zapisujemy sciezke, ktora zostala utworzona
					
				}
			}
			
			
			return super.zapisz(encja);
		}

		@Override
		@Transactional(readOnly=true)
		public Dokumentacja znajdzDokument(Long id) {
			
			return dokumentacjaDAO.findOne(id);
		}
	
		
	/*
	@Override
	public void preUtworz() throws ServiceException {
		throw new ServiceException("zlecenieUnikatowyNumer");
	}
	*/

	


	/*
	@Override
	public void preZapisz() throws ServiceException {
		
	}
	*/
	
	
}
