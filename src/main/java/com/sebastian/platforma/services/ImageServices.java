package com.sebastian.platforma.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sebastian.platforma.domain.Dokumentacja;


public class ImageServices extends HttpServlet{

	/** koncowka .xhtml
	 * file.name, id.dokument odnalezx i podac sciezke
	 */ 
	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = LoggerFactory.getLogger(ImageServices.class);
	
	@Autowired
	private IZlecenieService zlecenieService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
	  }
	
	
	//http://platforma/image.ximage?filename=obrazek.jpg - pliki tymczasowe
	//http://platforma/image.ximage?file=1 - pliki zapisane
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {

		String filename = req.getParameter("filename");
		String file=req.getParameter("file");
		logger.debug("Pobrano parametr filename {}",filename);
		logger.debug("Pobrano parametr file {}",file);
		
		String pathFile="C:\\Users\\Sebastian\\Desktop\\JAVA MICHAL\\Pliki.png";
		logger.debug("Zlecenie serwic="+zlecenieService);
		if(file!=null)
		{
			Long id=Long.parseLong(file);
			logger.debug("Pobieram dokument z id {}",id);
			Dokumentacja dokumentacja=zlecenieService.znajdzDokument(id);
			logger.debug("Pobrano dokument {}",dokumentacja);
			pathFile=dokumentacja.getSciezka();
			
		}
		
		if(filename!=null)
		{
			String temporaryPath=System.getProperty("java.io.tmpdir");
			logger.debug("Sciezka tymczasowe {}",System.getProperty("java.io.tmpdir"));
			pathFile=temporaryPath+filename;
			
		}
		//Dokumentacja patfToWeb = zlecenie.getDokumentacja().get(parses_image);
		logger.debug("Sciezka {}",pathFile);
		resp.setContentType("image/jpeg");
		
		

		File image = new File(pathFile);
		BufferedImage bi = ImageIO.read(image);
		
		OutputStream os = resp.getOutputStream();
		ImageIO.write(bi, "jpg", os);
		
		os.close();
	}
	
	
}
