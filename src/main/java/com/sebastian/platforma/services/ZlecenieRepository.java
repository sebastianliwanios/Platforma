package com.sebastian.platforma.services;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.domain.Zleceniodawca;

public interface ZlecenieRepository extends Repository<Zlecenie, Long>{

	List<Zlecenie> findZlecenieByNumerZleceniaAndZleceniodawca(Long numerZlecenia, Zleceniodawca zleceniodawca);
}
