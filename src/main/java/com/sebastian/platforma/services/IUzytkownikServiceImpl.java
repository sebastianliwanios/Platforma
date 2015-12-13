package com.sebastian.platforma.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sebastian.platforma.domain.Uzytkownik;

@Service
@Transactional
public class IUzytkownikServiceImpl extends AbstractCRUDService<Uzytkownik, Long> implements IUzytkownikService{

}
