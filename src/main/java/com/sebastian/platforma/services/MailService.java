package com.sebastian.platforma.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


import javax.mail.*;
import javax.mail.internet.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sebastian.platforma.domain.KontoMailowe;
import com.sebastian.platforma.domain.PlikMail;
import com.sebastian.platforma.domain.WiadomoscMail;

@Service
public class MailService extends AbstractCRUDService<KontoMailowe, Integer> implements IMailService{
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);

	@Transactional(readOnly=true)
	public boolean wyslijMail(WiadomoscMail wiadomosc)
	{
		List<KontoMailowe> lista=dao.findAll();
		logger.debug("Pobrano {} kont mailowych",lista.size());
		
		if(lista.size()==0)
		{
			logger.warn("Brak kont mailowych w aplikacji, nie kozna wyslac maila");
			return false;
		}
		
		KontoMailowe konto=lista.get(0);//pobieramy pierwsze konto m,ailowe z listy
		
		try
		{
			logger.debug("Twoprze połączenie dla konta {}",konto);
			Session session=utworzPolaczenie(konto);
			logger.debug("Tworze wiadomosc email dla {}",wiadomosc);
			MimeMessage msg=utworzWiadomosc(wiadomosc,konto,session);
			logger.debug("Rozpoczynam wysyłanie maila...");
			Transport.send(msg);
			logger.debug("Wysłano maila...");
			return true;
		}catch(MessagingException e)
		{
			logger.error("Send message error",e);
			return false;
		}
	}
	
	private Session utworzPolaczenie(KontoMailowe konto)
	{
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", konto.getStarttls());
		properties.put("mail.smtp.host", konto.getHostSMTP());
		properties.put("mail.smtp.port", konto.getPortSMTP());
		Session session = Session.getInstance(properties, new Authenticator() 
		{protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(konto.getMail(), konto.getHaslo());}});
		
		return session;
	}
	
	private MimeMessage utworzWiadomosc(WiadomoscMail wiadomosc,KontoMailowe konto,Session session)throws MessagingException
	{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(konto.getMail()));
		for(String odbiorca:wiadomosc.getOdbiorcy())
		{
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(odbiorca));
		}
		for(String odbiorcaWKopii:wiadomosc.getOdbiorcyWkopii())
		{
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(odbiorcaWKopii));
		}
		message.setSubject(wiadomosc.getTytul());
		
		Multipart multipart = new MimeMultipart();
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(wiadomosc.getTekst());
		
		if(!wiadomosc.getPliki().isEmpty())
		{
			MimeBodyPart bodyPart = new MimeBodyPart();
			
			try
			{
				for(PlikMail plikMail:wiadomosc.getPliki())
				{
					File plik = new File(new File(plikMail.getSciezkaPliku()), plikMail.getNazwaPliku());
					bodyPart.attachFile(plik);
				}
			}
			catch(IOException e)
			{
				throw new MessagingException("Attache file fails",e);
			}
			
			multipart.addBodyPart(bodyPart);
		}
		multipart.addBodyPart(messageBodyPart);
		
		message.setContent(multipart);
		
		return message;
	}
}
