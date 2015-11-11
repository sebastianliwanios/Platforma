package com.sebastian.platforma.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
public class Zlecenie implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@NotNull(message="Zleceniodawca jest wymagany")
	private Zleceniodawca zleceniodawca;
	
	@NotNull(message="Pole nie moze być puste")
	@Size(max=15, message="Numer nie moze być większy od {max} cyfr")
	private String numerZlecenia;
	
	@ManyToOne
	@NotNull(message="Ubezpieczyciel jest wymagany")
	private Ubezpieczyciel ubezpieczyciel;
	
	@Column(name="mail")
	@NotNull(message="Pole nie moze byc puste")
	@Email(message="Podano nieporawnego maila")
	private String mail;

	@Temporal(TemporalType.DATE)
	private Date dataWykonania;
	
	@Temporal(TemporalType.DATE)
	private Date dataOtrzymania;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataWyslania;
	
	@Column
	@NotNull(message="Pole nie moze być puste")
	@Size.List({
		@Size(max=100, message="Alias nie moze być większy od {max}"),
		@Size(min=2, message="Alias nie moze być mniejszy od {min}")
	})
	private String alias;
	
	@Size(max=200, message="Opis nie powinien zawierać więcej niz {max} znaków")
	private String opis;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true, fetch=FetchType.LAZY) // stworzy dokumentacje i przypisze do zleenia, podczas usuwania skasuje wszystkie powiazania i dokumenty
	private List<Dokumentacja> dokumentacja;
	
	@NotNull
	@ManyToOne
	private StatusZlecenia status;

	public Zlecenie() {}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Zlecenie)) {
			return false;
		}
		Zlecenie castOther = (Zlecenie) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("zleceniodawca", zleceniodawca)
				.append("numerZlecenia", numerZlecenia)
				.append("ubezpieczyciel", ubezpieczyciel).append("mail", mail)
				.append("dataWykonania", dataWykonania)
				.append("dataOtrzymania", dataOtrzymania)
				.append("dataWyslania", dataWyslania).append("alias", alias)
				.append("opis", opis).append("dokumentacja", dokumentacja)
				.append("status", status)
				.toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Zleceniodawca getZleceniodawca() {
		return zleceniodawca;
	}

	public void setZleceniodawca(Zleceniodawca zleceniodawca) {
		this.zleceniodawca = zleceniodawca;
	}

	public String getNumerZlecenia() {
		return numerZlecenia;
	}

	public void setNumerZlecenia(String numerZlecenia) {
		this.numerZlecenia = numerZlecenia;
	}

	public Ubezpieczyciel getUbezpieczyciel() {
		return ubezpieczyciel;
	}

	public void setUbezpieczyciel(Ubezpieczyciel ubezpieczyciel) {
		this.ubezpieczyciel = ubezpieczyciel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Date getDataWykonania() {
		return dataWykonania;
	}

	public void setDataWykonania(Date dataWykonania) {
		this.dataWykonania = dataWykonania;
	}

	public Date getDataOtrzymania() {
		return dataOtrzymania;
	}

	public void setDataOtrzymania(Date dataOtrzymania) {
		this.dataOtrzymania = dataOtrzymania;
	}

	public Date getDataWyslania() {
		return dataWyslania;
	}

	public void setDataWyslania(Date dataWyslania) {
		this.dataWyslania = dataWyslania;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public List<Dokumentacja> getDokumentacja() {
		return dokumentacja;
	}

	public void setDokumentacja(List<Dokumentacja> dokumentacja) {
		this.dokumentacja = dokumentacja;
	}

	public StatusZlecenia getStatus() {
		return status;
	}

	public void setStatus(StatusZlecenia status) {
		this.status = status;
	}
	
	
}
