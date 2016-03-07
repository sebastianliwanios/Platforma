package com.sebastian.platforma.domain.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.sebastian.platforma.domain.Zlecenie;

public class GenericFilter<T> implements Specification<T> {

	private static final Logger logger=LoggerFactory.getLogger(GenericFilter.class); 
	
	private Map<String, Object> filters;
	
	public  GenericFilter(Map<String, Object> filters) {
		this.filters=filters;
	}
	
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
		//JPA Criteria API
		List<Predicate> predicates=new ArrayList<Predicate>();
		
		logger.debug("filters {}",filters);
		
		for(Map.Entry<String, Object> entry:filters.entrySet())
		{
			
			try
			{
				Path<?> path=null;
				
				logger.debug("key {}",entry.getKey());
				logger.debug("value class {}",entry.getValue().getClass());
				
				if(entry.getKey().indexOf(".")!=-1)
				{
					
					String[] names=entry.getKey().split("\\.");
					path=root.get(names[0]);
					for(int i=1;i<names.length;i++)
					{
						path=path.get(names[i]);
					}
				}
				else
					path=root.get(entry.getKey());
				
				predicates.add(cb.like(((Expression<String>)path).as(String.class), "%"+entry.getValue()+"%"));
			}
			catch(Exception e)
			{
				logger.error(" error predicate ",e);
			}
		}
		
		logger.debug("predicates {}=",predicates);
		logger.debug("and {}",cb.and(predicates.toArray(new Predicate[]{})));
		return cb.and(predicates.toArray(new Predicate[]{}));
	}

	
}
