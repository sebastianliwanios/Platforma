package com.sebastian.platforma.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Zleceniodawca implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Short id;
	
	@NotNull
	@Size.List({
		@Size(max=25, message="Nazwa powinna zawierać max {max} znaków"),
		@Size(min=1, message="Nazwa powinna zawierać min {min} znak")
	}) 
	private String nazwa;
	
	@Size(max=100, message="Opis powinien zawierać do {max} znaków")
	private String opis;
	
	public Zleceniodawca() {}
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Zleceniodawca)) {
			return false;
		}
		Zleceniodawca castOther = (Zleceniodawca) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("nazwa", nazwa).append("opis", opis).toString();
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	};

	
	
	
}
