package com.sebastian.platforma.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sebastian.platforma.domain.Zleceniodawca;

@Service
@Transactional
public class ZleceniodawcaServiceImpl extends AbstractCRUDService<Zleceniodawca, Short> implements IZleceniodawcaService{

}
