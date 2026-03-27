package br.com.ccroccia.dao.generics;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import annotation.*;
import br.com.ccroccia.dao.Persistent;
import br.com.ccroccia.exceptions.KeyTypeNotFoundException;
import br.com.ccrocia.dao.generic.jdbc.ConnectionFactory;

public abstract class GenericDAO<T extends Persistent, E extends Serializable> implements IGenericDAO<T,E> {

    public abstract Class<T> getTypeClass();
    
    protected abstract String getQueryInsert();
    
    protected abstract String getQueryDelete();
    
    protected abstract String getQueryUpdate();
    
    protected abstract void setParametersInsert(PreparedStatement stmInsert, T entity) throws SQLException;
    
    protected abstract void setParametersDelete(PreparedStatement stmDelete, E value) throws SQLException ;
    
    protected abstract void setParametersUpdate(PreparedStatement stmUpdate, E value) throws SQLException;
    
    protected abstract void setParametersSelect(PreparedStatement stmUpdate, E value) throws SQLException;
    
    public GenericDAO() {
    	
    }
  
    /// =============REGISTER =================
    @Override
    public Boolean register(T entity) throws KeyTypeNotFoundException, Exception {
    	Connection connection = null;
    	PreparedStatement stm = null;
    	try {
    		connection = getConnection();
    		stm        = connection.prepareStatement(getQueryInsert(), Statement.RETURN_GENERATED_KEYS);
    		setParametersInsert(stm, entity);
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
 
    // ================== SELECT =======================================
    public T find(E value) throws Exception {
       	Connection connection = null;
    	PreparedStatement stm = null;
    	try {
    		connection = getConnection();
    		stm		   = connection.prepareStatement("SELECT * FROM " 
    		+ getTableName() + " WHERE " + getColumnName().columnName() + " = ? ");
    		setParametersSelect(stm, value);
    		ResultSet rs = stm.executeQuery();
    		if(rs.next()) {
    			T entity = getTypeClass().getConstructor().newInstance();
    			Field[] fields = entity.getClass().getDeclaredFields();
    			for(Field f:fields) {
    				Column column = f.getDeclaredAnnotation(Column.class);
    				String columnName = column.columnName();
    				String setMethod  = column.method();
    				Class<?> type = f.getType();
    				try {
    					//The method getMethod allow us to find methods with String, that
    					// represents method name and type parameters
    					Method method = entity.getClass().getMethod(setMethod, type);
    					setValueByType(entity, method, type, rs, columnName);
    				} catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
    			        throw new Exception("ERRO CONSULTANDO OBJETO ", e);
    			    }
    				
    				
    			}
    			return entity;
    		}
    	} catch(Exception e) {
    		throw new Exception("Erro ao se conectar com o banco ", e);
    	} finally {
			closeConnection(connection, stm, null);
		}
		return null;
    	
    }
 //=================== DELETE =========================================================   
    public Boolean delete(E value) throws Exception {
    	Connection connection = null;
    	PreparedStatement stm = null;
    	try {
    		connection = getConnection();
    		stm = connection.prepareStatement(getQueryDelete() + " WHERE " 
    		+ getColumnName().columnName() + " = " + " ? ");
    		setParametersDelete(stm, value);
    		if(stm.executeUpdate() > 0) {
    			return true;
    		}
    		return false;
    	} catch(SQLException e) {
			throw new Exception("Erro ao excluir", e);
		} finally {
			closeConnection(connection, stm, null);
		}
    }
    
  // ================= UPDATE ========================================================== 
    public Boolean update(E value) throws Exception {
    	Connection connection = null;
    	PreparedStatement stm = null;
    	try {
    		connection = getConnection();
    		stm = connection.prepareStatement(getQueryUpdate() + " WHERE " 
    		+ getColumnName().columnName() + " = " + " ? ");
    		setParametersUpdate(stm, value);
    		if(stm.executeUpdate() > 0) {
    			return true;
    		}
    		return false;
    	} catch(SQLException e) {
			throw new Exception("Erro ao atualizar", e);
		} finally {
			closeConnection(connection, stm, null);
		}
    }
    
  // ========================== SELECT ALL =============================================
    
    
    public List<T> findAll() throws Exception {
       	Connection connection 	= null;
    	PreparedStatement stm	= null;
    	List<T> list 			= new ArrayList<T>();
    	try {
    		connection = getConnection();
    		stm		   = connection.prepareStatement("SELECT * FROM " + getTableName());
    		ResultSet rs = stm.executeQuery();
    		while(rs.next()) {
    			T entity = getTypeClass().getConstructor().newInstance();
    			list.add(entity);
    			
    		}
    		return list;
    	} catch(Exception e) {
    		throw new Exception("Erro ao se conectar com o banco ", e);
    	} finally {
			closeConnection(connection, stm, null);
		}
    }
    
    
    
    
    
    
  /// ====================== OTHER METHODS   ===================================================
    private void setValueByType(T entity, Method method, Class<?> fieldClass, ResultSet rs, String fieldName)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, Exception {

        if (fieldClass.equals(Integer.class)) {
            Integer val = rs.getInt(fieldName);
            method.invoke(entity, val);
        } else if (fieldClass.equals(Long.class)) {
            Long val = rs.getLong(fieldName);
            method.invoke(entity, val);
        } else if (fieldClass.equals(Double.class)) {
            Double val = rs.getDouble(fieldName);
            method.invoke(entity, val);
        } else if (fieldClass.equals(Short.class)) {
            Short val = rs.getShort(fieldName);
            method.invoke(entity, val);
        } else if (fieldClass.equals(BigDecimal.class)) {
            BigDecimal val = rs.getBigDecimal(fieldName);
            method.invoke(entity, val);
        } else if (fieldClass.equals(String.class)) {
            String val = rs.getString(fieldName);
            method.invoke(entity, val);
        } else {
            throw new Exception("UNKNOWN CLASS TYPE: " + fieldClass);
        }
    }

    //Get the column that has annotation of pk
	private Column getColumnName() {
		Class<T> entity = getTypeClass();
		Field[] fields = entity.getDeclaredFields();
		for(Field f: fields) {
			if(f.getAnnotation(KeyType.class) != null) {
				Column column = f.getAnnotation(Column.class);	
				return column;
			}
		}
		return null;
	}

	private String getTableName() {
		Class<T> entity = getTypeClass();
		Table table = entity.getAnnotation(Table.class);
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
