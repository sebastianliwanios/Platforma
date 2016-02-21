package com.sebastian.platforma.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class KontoMailowe implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private Boolean starttls;
	
	@Email(message="Niepoprawny adres email")
	@NotNull
	@Size(min=5, message="Niepoprawny adres email")
	private String mail;
	
	@Size.List({
		@Size(min=5, message="Hasło musi zawierać co najmniej {min} znaków"),
		@Size(max=21, message="Hasło powinno zawierać max {max} znaków")
	})
	@NotNull
	private String haslo;
	
	@Pattern(regexp="[0-9]{1,6}", message="Port musi zawierać liczby od 0-9 do 99999")
	@NotNull
	private String portSMTP;
	
	@Size.List({
		@Size(min=8, message="host musi zawierać co najmniej {min} znaków"),
		@Size(max=40, message="host powinno zawierać max {max} znaków")
	})
	@NotNull
	private String hostSMTP;
	
	public KontoMailowe() {}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("mail", mail).toString();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof KontoMailowe)) {
			return false;
		}
		KontoMailowe castOther = (KontoMailowe) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public String getPortSMTP() {
		return portSMTP;
	}

	public void setPortSMTP(String portSMTP) {
		this.portSMTP = portSMTP;
	}

	public String getHostSMTP() {
		return hostSMTP;
	}

	public void setHostSMTP(String hostSMTP) {
		this.hostSMTP = hostSMTP;
	}

	public Boolean getStarttls() {
		return starttls;
	}

	public void setStarttls(Boolean starttls) {
		this.starttls = starttls;
	}
	
	
	
}
