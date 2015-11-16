package com.sebastian.platforma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebastian.platforma.domain.Uzytkownik;

public interface IUzytkownikDAO extends JpaRepository<Uzytkownik, Long>{

}
