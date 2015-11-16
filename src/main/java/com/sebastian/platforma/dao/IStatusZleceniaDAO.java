package com.sebastian.platforma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebastian.platforma.domain.StatusZlecenia;

public interface IStatusZleceniaDAO extends JpaRepository<StatusZlecenia, Short>{

}
