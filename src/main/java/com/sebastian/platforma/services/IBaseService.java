package com.sebastian.platforma.services;

import java.io.Serializable;
import java.util.List;
/**
 * 1wszy bazowy -  Uzywamy w przypadku kiedy chcemy uzyc tylko metody odczytujace
 * @author Sebastian
 *
 * @param <T>
 * @param <K>
 */
public interface IBaseService<T extends Serializable,K extends Serializable> {

	public T zanjdzPoID(K id);
	public List<T> znajdzWszystkie();
	public T aktualizuj(T obj);
}
