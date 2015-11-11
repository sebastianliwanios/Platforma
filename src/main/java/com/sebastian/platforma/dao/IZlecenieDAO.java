package com.sebastian.platforma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebastian.platforma.domain.Zlecenie;

public interface IZlecenieDAO extends JpaRepository<Zlecenie, String> {

}
