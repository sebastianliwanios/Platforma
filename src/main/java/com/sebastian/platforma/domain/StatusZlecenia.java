package com.sebastian.platforma.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class StatusZlecenia implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Short id;
	
	@Size(max=20)
	@Column(unique=true)
	private String nazwa;

	public StatusZlecenia() {}
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof StatusZlecenia)) {
			return false;
		}
		StatusZlecenia castOther = (StatusZlecenia) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("nazwa", nazwa).toString();
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

}
