package com.sebastian.platforma.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sebastian.platforma.domain.Zlecenie;

public interface IZlecenieDAO extends JpaRepository<Zlecenie, Integer>,JpaSpecificationExecutor<Zlecenie> {

	List<Zlecenie> findByNumerZlecenia(String numerZlecenia);
	Page<Zlecenie> findAll(Specification<Zlecenie> filters,Pageable page);//PageRequest
	Page<Zlecenie> findAll(Pageable page );
}
