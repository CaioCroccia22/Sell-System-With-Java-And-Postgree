package br.com.ccroccia.dao.generics;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import annotation.*;
import br.com.ccroccia.dao.Persistent;
import br.com.ccroccia.exceptions.KeyTypeNotFoundException;
import br.com.ccrocia.dao.generic.jdbc.ConnectionFactory;

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
    
    @Override
    public Boolean register(T entity) throws KeyTypeNotFoundException, Exception {
    	Connection connection = null;
    	PreparedStatement stm = null;
    	try {
    		connection = getConnection();
    		stm        = connection.prepareStatement(getQueryInsert(), Statement.RETURN_GENERATED_KEYS);
    		if(stm.executeUpdate() > 0) {
    			try(ResultSet rs = stm.getGeneratedKeys()){
    				if(rs.next()) {
    					Persistent per = (Persistent) entity;
    					per.setId(rs.getLong(1));
    				}
    			}
    			return true;
    		}
    	}catch(Exception e) {
    		throw new Exception("Erro Cadastrando Objeto", e);
    	}finally {
    		closeConnection(connection, stm, null);
    	}
		return false;
    }
    
    public T find(E value) {
       	Connection connection = null;
    	PreparedStatement stm = null;
    	try {
    		connection = getConnection();
    		stm		   = connection.prepareStatement("SELECT * FROM" 
    		+ getTableName() + "WHERE" + getColumnName() + "= ?");
    		ResultSet rs = stm.executeQuery();
    		if(!rs.wasNull()) {
    			T entity = getTypeClass().getConstructor().newInstance();
    			Field[] fields = entity.getClass().getFields();
    			for(Field f:fields) {
    				Column column = f.getDeclaredAnnotation(Column.class);
    				String columnName = column.columnName();
    				String setMethod  = column.method();
    				Class<?> type = f.getType();
    				try {
    					//The method getMethod allow us to find methods with String, that
    					// represents method name and type parameters
    					Method method = entity.getClass().getMethod(setMethod, type)
    					setValueByType(entity, method, type, rs, columnName);
    				} catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
    					throw new Exception("ERRO CONSULTANDO OBJETO ", e);
	                } catch (KeyTypeNotFoundException e) {
	                	throw new Exception("ERRO CONSULTANDO OBJETO ", e);
					}
    				
    			}
    		}
    	}
		return null;
    	
    }

    //Get the column that has annotation of pk
	private String getColumnName() {
		Class<T> entity = getTypeClass();
		Field[] fields = entity.getFields();
		for(Field f: fields) {
			if(f.getAnnotation(KeyType.class) != null) {
				Column column = entity.getClass().getAnnotation(Column.class);	
				return column.columnName();
			}
		}
		return null;
	}

	private String getTableName() {
		Class<T> entity = getTypeClass();
		Table table = entity.getClass().getAnnotation(Table.class);
		return table.tableName();
	}

	private static Connection getConnection() throws SQLException {
		return ConnectionFactory.getConnection();
	}
	
	private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) throws SQLException {
		try {
			if(rs != null && !rs.isClosed()) {
				rs.close();			
			}
			if(stm != null && !stm.isClosed()) {
				stm.close();			
			}
			if(connection != null && !connection.isClosed()) {
				connection.close();			
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
