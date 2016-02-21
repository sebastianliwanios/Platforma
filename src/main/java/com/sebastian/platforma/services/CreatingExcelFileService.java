package com.sebastian.platforma.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.domain.Zleceniodawca;

@Service
public class CreatingExcelFileService {
	
	private final static Logger logger = LoggerFactory.getLogger(CreatingExcelFileService.class);
	
	public CreatingExcelFileService(){
		logger.debug("Wywołuje konstruktor {}", CreatingExcelFileService.class);
	}
	
	public void generujZestawienie(List<Zlecenie> zlecenie)//,Zleceniodawca zleceniodawca,Date data)
	{
	
		//SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
		
		//String sciezka = "C:"+File.separator+"Platforma"+File.separator+format.format(data)+File.separator+zleceniodawca.getNazwa()+File.separator;
		String nazwaPliku = "Zestawienie.xls";
		String sciezka = "C:"+File.separator+"Platforma"+File.separator;
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Zestawienie");
		
		generujeNaglowki(sheet,0);
		generujDaneOZleceniu(zlecenie,sheet,1);
		zapiszPlik(workbook,sciezka,nazwaPliku);
	}
	
	private void generujeNaglowki(HSSFSheet sheet,int numerWiersza)
	{
		HSSFRow wiersz = sheet.createRow(numerWiersza);
		String[] naglowki=new String[]{"LP", "Nr Zlecenia", "Opis", "Miejscowość", "Data", "Ilość km", "Wartość za km", "Stawka"};
		
		for (int i = 0; i<naglowki.length; i++) {
			HSSFCell komorka=wiersz.createCell(i);
			komorka.setCellValue(naglowki[i]);
		}
	}
	
	private void generujDaneOZleceniu(List<Zlecenie> zlecenie,HSSFSheet sheet,int numerWierszaPoczatkowego)
	{
		int lp = 1;
		int nrWiersza = numerWierszaPoczatkowego;
		for (Zlecenie z:zlecenie){
			HSSFRow wiersz = sheet.createRow(nrWiersza);
			HSSFCell komorka=wiersz.createCell(0);
			komorka.setCellValue(lp);
			
			komorka=wiersz.createCell(1);
			komorka.setCellValue(z.getNumerZlecenia());
			
			komorka=wiersz.createCell(2);
			komorka.setCellValue(z.getAlias());
			
			komorka=wiersz.createCell(3);
			komorka.setCellValue(z.getMiejscowosc());
			
			komorka=wiersz.createCell(4);
			komorka.setCellValue(z.getDataOtrzymania());
			
			komorka=wiersz.createCell(5);
			komorka.setCellValue("");
			
			komorka=wiersz.createCell(6);
			komorka.setCellValue("");
			
			komorka=wiersz.createCell(7);
			komorka.setCellValue(z.getStawka().doubleValue());
			
			nrWiersza++;
			lp++;
		}
		
	}
	
	private boolean zapiszPlik(HSSFWorkbook workbook,String sciezka,String nazwaPliku)
	{
		FileOutputStream zapisPliku = null;
		try {
			zapisPliku = new FileOutputStream(sciezka+nazwaPliku);
			workbook.write(zapisPliku);
			zapisPliku.close();
			logger.debug("Pomyślnie zapisano plik excel, sciezka {}", sciezka+nazwaPliku);
			return true;
		} catch (IOException e) {
			logger.error("Nie zapisano pliku Zestawienie.xls", e);
		} 
		return false;
	}
	
	
}
