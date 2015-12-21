package com.sebastian.platforma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebastian.platforma.domain.Zlecenie;

public interface IZlecenieDAO extends JpaRepository<Zlecenie, Integer> {

	List<Zlecenie> findByNumerZlecenia(String numerZlecenia);
}
