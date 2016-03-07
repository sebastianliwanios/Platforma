package com.sebastian.platforma.services;


import java.awt.image.BufferedImage;
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
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.sebastian.platforma.dao.IDokumentacjaDAO;
import com.sebastian.platforma.dao.IZlecenieDAO;
import com.sebastian.platforma.domain.Dokumentacja;
import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.domain.filters.GenericFilter;

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
		
		if(zlecenie.getDokumentacja()!=null && !zlecenie.getDokumentacja().isEmpty())
		{
			for (Dokumentacja d:zlecenie.getDokumentacja()) {//d - sciezka-> c:tmp00123.tmp, petla zapisuje wszystkie zaznaczone pliki
				File tymczasowy = new File(d.getSciezka()); // pobieramy nazwę wszystkich plikow tymczasowych
				File plikDocelowy = new File(sciezka+d.getNazwa()); // tworzymy sciezke do plikow docelowych
				plikDocelowy.mkdirs(); // tworzymy folder wraz z sciezką docelową
				
				try {
					logger.debug("plik tymczasowy to {} oraz docelowy to: {}",tymczasowy, plikDocelowy);
					Files.copy(tymczasowy.toPath(), plikDocelowy.toPath(), StandardCopyOption.REPLACE_EXISTING); // kopiujemy pliki z folderu tymczasowego do docelowego
					if(d.isObrazek())
						zapiszObrazek(plikDocelowy);
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
	/*
	 * Archiwizacja plików
	 * ---------------------------
	 */
				String sciezkaBazowaZip = "C:"+File.separator+"Platforma"+File.separator+"ZipFiles"+File.separator+format.format(zlecenie.getDataOtrzymania())
					+File.separator+zlecenie.getZleceniodawca().getNazwa()+File.separator; 
	
				String nazwaPliku = sciezkaBazowaZip+zlecenie.getNumerZlecenia()+"_"
						+zlecenie.getUbezpieczyciel().getNazwa()+"_"+zlecenie.getAlias()+".zip";
				
				try {
					File fileBazowy = new File(sciezkaBazowaZip);// tworzymy sciezke bazowa w ktorej bedziemy zapisywac pliki
					File file = new File(nazwaPliku); // tworzymy folder jak ma sie nazywac gdzie zapisane zostana pliki
					logger.debug("Sciezka file: {}", file.getAbsolutePath());
					zlecenie.setSciezkaZip(nazwaPliku);
					boolean isZapisany = fileBazowy.mkdirs();
					if (!isZapisany){
						logger.debug("Nie zapisano archiwum");
					}
					//zlecenie.setZipSciezka(sciezkaZip);
					BufferedInputStream bis = null;
					BufferedOutputStream bos = null;
					FileOutputStream out = new FileOutputStream(file); // wskazujemy miejsce gdzie maja byc zapisane pliki
					bos = new BufferedOutputStream(out);
					ZipOutputStream zos = new ZipOutputStream(bos);
					byte data[] = new byte[2048];
					
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
	
		private void zapiszObrazek(File plik)
		{
			try
			{
				logger.debug("zapiszObrazek(plik={})",plik);
				BufferedImage obrazek = ImageIO.read(plik);
				int szerokosc=obrazek.getWidth();
				int wysokosc=obrazek.getHeight();
				logger.debug("Rozdzielczosc obrazka {} na {}",szerokosc,wysokosc);
			
				if(!(szerokosc>=1600 && wysokosc>=1200))
				{
					logger.debug("Pomijam zmiane rozdzielczosci obrazka");
					return;
				}
				
				String rozszerzenie=plik.getName().substring(plik.getName().lastIndexOf(".")+1);
				logger.debug("Rozszerzenie pliku {}",rozszerzenie);
				if("jpg".equalsIgnoreCase(rozszerzenie))
				{
					Thumbnails.of(obrazek).size(1600, 1200).outputFormat("jpg").allowOverwrite(true).toFile(plik);
					logger.debug("Zmieniono rozdzielczosc obrazka");
				}
				else if("png".equalsIgnoreCase(rozszerzenie))
				{
					Thumbnails.of(obrazek).size(1600, 1200).outputFormat("png").allowOverwrite(true).toFile(plik);
					logger.debug("Zmieniono rozdzielczosc obrazka");
				}
			}
			catch(IOException e)
			{
				logger.error("Nie udało się zmienic rozmiaru obrazka ",e);
			}
		 
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
			if(encja.getDokumentacja()!=null && !encja.getDokumentacja().isEmpty())
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
						if(d.isObrazek())
							zapiszObrazek(plikDocelowy);
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
				
			/*
			* Archiwizacja plików
			* ---------------------------
			*/
				
				SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
				
				String sciezkaBazowaZip = "C:"+File.separator+"Platforma"+File.separator+"ZipFiles"+File.separator+format.format(encja.getDataOtrzymania())
								+File.separator+encja.getZleceniodawca().getNazwa()+File.separator; 
				
				String nazwaArchiwumZip = sciezkaBazowaZip+encja.getNumerZlecenia()+"_"
									+encja.getUbezpieczyciel().getNazwa()+"_"+encja.getAlias()+".zip";
				
				try {
					File fileBazowy = new File(sciezkaBazowaZip);// tworzymy sciezke bazowa w ktorej bedziemy zapisywac pliki
					File file = new File(nazwaArchiwumZip); // tworzymy folder jak ma sie nazywac gdzie zapisane zostana pliki
					//File fileIstniejacy = new File(encja.getZipSciezka());
					//logger.debug("Sciezka do archiwum {} ",fileIstniejacy );
					//boolean isDeleted = fileIstniejacy.delete();
					File stareArchiwum = new File(encja.getSciezkaZip());
					if (stareArchiwum.delete()) {
						logger.debug("Skasowano stare archiwum {}",stareArchiwum.getAbsolutePath());
					}
					else {
						logger.error("Nie udało się usunąć starego archiwum {}", stareArchiwum.getAbsolutePath());
					}
					logger.debug("Plik o nazwie {}",file.getName());
					logger.debug("Sciezka file: {}", file.getAbsolutePath());
					//logger.debug("Czy plik zostal skasowany ? {}",isDeleted);
					fileBazowy.mkdirs();
					BufferedInputStream bis = null;
					BufferedOutputStream bos = null;
					FileOutputStream out = new FileOutputStream(file); // wskazujemy miejsce gdzie maja byc zapisane pliki
					bos = new BufferedOutputStream(out);
					ZipOutputStream zos = new ZipOutputStream(bos);
					byte data[] = new byte[2048];
						
					for (Dokumentacja d:encja.getDokumentacja()) { // przeszukujemy petlą całą dokumentacje 
						File f = new File(d.getSciezka()); // wskazujemy sciezke gdzie znajduja sie pliki, ktore maja byc skompresowane
						logger.debug(d.getNazwa());
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
	
						logger.debug("pomyślnie zapisano pliki");
					}
					zos.close();
				} catch (FileNotFoundException e) {
					logger.debug("Nie odnaleziono pliku ",e);
					
				} catch (IOException e) {
					logger.debug("Nie zapisano pliku",e);
					
				} catch (Exception e) {
					logger.error("Error unknown",e);
				}	
			}
			return super.zapisz(encja);
		}

		@Override
		@Transactional(readOnly=true)
		public Dokumentacja znajdzDokument(Long id) {
			
			return dokumentacjaDAO.findOne(id);
		}
	
	@Override	
	@Transactional(readOnly=true)	
	public Page<Zlecenie> filtrujZlecenia(PageRequest stronicowanie,GenericFilter filter)
	{
		if(filter==null)
			return zlecRepo.findAll(stronicowanie);
		else
			return zlecRepo.findAll(filter,stronicowanie);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Zlecenie> znajdzWszystkie() {
		logger.debug("Wyszukuje listę zleceń...");
		return super.znajdzWszystkie();
	}
	
	
	
	
}
