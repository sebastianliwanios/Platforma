package com.sebastian.platforma.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Dokumentacja implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size.List({
		@Size(min=2, message="Nazwa moze mieć min {min} znaków"),
		@Size(max=100, message="Nazwa moze mieć max {max} znaków")	
			})
	private String nazwa;
	
	@NotNull
	@Size(min=1, max=512)
	private String sciezka;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date dataDodania;
	
	@Size(max=100)
	private String opis;
	
	@Transient
	private boolean tymczasowy;
	
	@Transient
	private String nazwaTymczsowegoPliku;
	
	public Dokumentacja() {}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Dokumentacja)) {
			return false;
		}
		Dokumentacja castOther = (Dokumentacja) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("nazwa", nazwa).append("sciezka", sciezka)
				.append("dataDodania", dataDodania).append("opis", opis)
				.toString();
	}
	
	public boolean isObrazek()
	{
		if(nazwa==null)
			return false;
		
		if(nazwaKonczySieNa("png","jpg","jpeg","gif","bitmap","tif"))
			return true;
		return false;
	}
	
	private boolean nazwaKonczySieNa(String... rozszerzenia)
	{
		for(String rozszerzenie:rozszerzenia)
		{
			if(nazwa.endsWith(rozszerzenie))
				return true;
		}
		
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getSciezka() {
		return sciezka;
	}

	public void setSciezka(String sciezka) {
		this.sciezka = sciezka;
	}

	public Date getDataDodania() {
		return dataDodania;
	}

	public void setDataDodania(Date dataDodania) {
		this.dataDodania = dataDodania;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public boolean isTymczasowy() {
		return tymczasowy;
	}

	public void setTymczasowy(boolean tymczasowy) {
		this.tymczasowy = tymczasowy;
	}

	public String getNazwaTymczsowegoPliku() {
		return nazwaTymczsowegoPliku;
	}

	public void setNazwaTymczsowegoPliku(String nazwaTymczsowegoPliku) {
		this.nazwaTymczsowegoPliku = nazwaTymczsowegoPliku;
	}
	
	
	
}
