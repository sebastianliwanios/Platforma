package com.sebastian.platforma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebastian.platforma.domain.KontoMailowe;

public interface IKontoMailoweDAO extends JpaRepository<KontoMailowe, Integer>{
	
}
