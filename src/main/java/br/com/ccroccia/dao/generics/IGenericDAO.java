package br.com.ccroccia.dao.generics;

import br.com.ccroccia.dao.Persistent;
import br.com.ccroccia.exceptions.KeyTypeNotFoundException;

import java.io.Serializable;
import java.util.Collection;

/**
 * Generic interface for CRUD methods (Create, Read, Update and Delete)
 */
public interface IGenericDAO <T extends Persistent, E extends Serializable> {


    public Boolean register(T entity) throws KeyTypeNotFoundException, Exception;


    public Boolean delete(E value) throws Exception;


    public Boolean update(E value) throws KeyTypeNotFoundException, Exception;


    public T find(E value) throws Exception;


    public Collection<T> findAll() throws Exception;
}
