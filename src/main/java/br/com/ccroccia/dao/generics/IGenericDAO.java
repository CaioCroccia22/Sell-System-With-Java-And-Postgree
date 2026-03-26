package br.com.ccroccia.dao.generics;

import br.com.ccroccia.dao.Persistent;
import br.com.ccroccia.exceptions.KeyTypeNotFoundException;

import java.io.Serializable;
import java.util.Collection;

/**
 * Generic interface for CRUD methods (Create, Read, Update and Delete)
 */
public interface IGenericDAO <T extends Persistent, E extends Serializable> {


    public Integer register(T entity) throws KeyTypeNotFoundException;


    public Integer delete(E value);


    public Integer update(T entity) throws KeyTypeNotFoundException;


    public T find(E value);


    public Collection<T> findAll();
}
