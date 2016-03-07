package com.sebastian.platforma.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.sebastian.platforma.controllers.JSFUtility;
import com.sebastian.platforma.domain.Zlecenie;
import com.sebastian.platforma.domain.filters.GenericFilter;
import com.sebastian.platforma.services.IZlecenieService;

public class ZlecenieModel extends LazyDataModel<Zlecenie> {

	private static final Logger logger=LoggerFactory.getLogger(ZlecenieModel.class);
	private static final long serialVersionUID = 1L;

	public ZlecenieModel() {
		logger.debug("creating");
	}

	@Override
	public List<Zlecenie> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters) {
		
		logger.debug("first={}, pageSize={},sortField={},sortOrder={} filters={}",first,pageSize,sortField,sortOrder,filters);
		
		int numerStrona=first/pageSize;
		
		PageRequest str=null;
		if(sortField==null)
			str=new PageRequest(numerStrona, pageSize);
		else
		{
			Direction direction=null;
			if(sortOrder==SortOrder.ASCENDING)
				direction=Direction.ASC;
			else if(sortOrder==SortOrder.DESCENDING)
				direction=Direction.DESC;
				
			
			str=new PageRequest(numerStrona, pageSize, direction, sortField);
		}
		
		GenericFilter<Zlecenie> filter=null;
		
		if(!filters.isEmpty())
			filter=new GenericFilter<Zlecenie>(filters);
		//str=new PageRequest(numerStrona, pageSize);
		Page<Zlecenie> strona= JSFUtility.findService(IZlecenieService.class).filtrujZlecenia(str, filter);
		
		long liczbaWszytskichWierszy=strona.getTotalElements();
		setRowCount((int)liczbaWszytskichWierszy);
		logger.debug("liczba wierszy {}",strona.getTotalElements());
		logger.debug("Liczba stron {}",strona.getTotalPages());
		logger.debug("Strona {}",strona.getNumber());
		logger.debug("wynik {}",strona.getContent());
		return strona.getContent();
	}
	
	

	

	@Override
	public Object getRowKey(Zlecenie object) {
		
		return super.getRowKey(object);
	}

	
	
}
