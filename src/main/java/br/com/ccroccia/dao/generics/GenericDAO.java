package br.com.ccroccia.dao.generics;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import annotation.KeyType;
import br.com.ccroccia.dao.Persistent;
import br.com.ccroccia.exceptions.KeyTypeNotFoundException;

/**
 * @author rodrigo.pires
 *
 * Generic class that implements the generic interface with CRUD methods
 */
public abstract class GenericDAO<T extends Persistent, E extends Serializable> implements IGenericDAO<T,E> {

    public abstract Class<T> getTypeClass();
    
    public abstract void updateData(T entity, T existEntity);
    
    protected abstract String getQueryInsert();
    
    protected abstract String getQueryDelete();
    
    protected abstract String getQueryUpdate();
    
    protected abstract void setParametersInsert(PreparedStatement stmInsert, T entity);
    
    protected abstract void setParametersDelete(PreparedStatement stmDelete, T entity);
    
    protected abstract void setParametersUpdate(PreparedStatement stmUpdate, T entity);
    
    protected abstract void setParametersSelect(PreparedStatement stmUpdate, T entity);
    
    public GenericDAO() {
    	
    }
}
