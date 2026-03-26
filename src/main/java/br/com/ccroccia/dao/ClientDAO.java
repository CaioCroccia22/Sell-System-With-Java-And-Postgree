package br.com.ccroccia.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ccroccia.dao.generics.GenericDAO;
import br.com.ccroccia.dao.generics.IGenericDAO;
import br.com.ccroccia.domain.Client;
import br.com.ccrocia.dao.generic.jdbc.ConnectionFactory;

public class ClientDAO extends GenericDAO<Client, Long> implements IGenericDAO<Client,Long> {

	@Override
	protected String getQueryInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO CLIENTE ");
		sb.append("(ID, NOME, CPF, TEL, ENDERECO, NUMERO");
		sb.append("VALUES (nextval('sq_cliente'), ?,?, ?,?,?,?,?");
		return sb.toString();
	}
	
	
	
	
	@Override
	public Integer Register(Client client) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlInsert();
			stm = connection.prepareStatement(sql);
			getInsertData(stm, client);
			return stm.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}


	private String getSqlInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO CLIENTE (cd_cliente, cpf,nm_cliente) ");
		sb.append("VALUES (nextval('sq_cliente'), ?,?)");
		return sb.toString();
	}
	
	private void getInsertData(PreparedStatement stm, Client client) throws SQLException {
		stm.setLong(1, client.getCpf());
		stm.setString(2, client.getName());
	
	}

	@Override
	public Client findByCPF(Long cpf) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Client client =	null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelect();
			stm = connection.prepareStatement(sql);
			getSelectData(stm, cpf);
			rs = stm.executeQuery();
			
			if(rs.next()) {
				client = new Client();
				String name = rs.getString("nm_cliente");
				Long id = rs.getLong("cd_cliente");
				client.setId(id);
				client.setName(name);
				client.setCpf(cpf);
				
			}
			
		}catch(Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		return client;
	}

	private void getSelectData(PreparedStatement stm, Long cpf) throws SQLException {
		stm.setLong(1, cpf);
		
	}


	private String getSqlSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM CLIENTE ");
		sb.append("WHERE cpf = ?");
		return sb.toString();
	}


	@Override
	public Integer delete(Long id) throws Exception {
			Connection connection = null;
			PreparedStatement stm = null;
			try {
				connection = ConnectionFactory.getConnection();
				String sql = deleteSqlUpdate();
				stm =  connection.prepareStatement(sql);
				getDeleteData(stm, id);
				return stm.executeUpdate();
			}catch(Exception e) {
				throw e;
			} finally {
				closeConnection(connection, stm, null);
			}
	}

	private void getDeleteData(PreparedStatement stm, Long id) throws SQLException {
		stm.setLong(1, id);
		
	}


	private String deleteSqlUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM CLIENTE WHERE ");
		sb.append("cpf = ?");
		return sb.toString();
	}


	@Override
	public Integer update(Client client) throws SQLException {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlUpdate();
			stm = connection.prepareStatement(sql);
			getUpdateData(stm, client);
			return stm.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	private void getUpdateData(PreparedStatement stm, Client client) throws SQLException {
		stm.setString(1, client.getName());
		stm.setLong(2, client.getCpf());
		stm.setLong(3, client.getId());
		
	}

	private String getSqlUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE CLIENTE SET ");
		sb.append("nm_cliente = ?, cpf = ? ");
		sb.append("WHERE cd_cliente = ?");
		return sb.toString();
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


	@Override
	public List<Client> getAll() throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs		  = null;
		Client client		  = null;
		List<Client> list     = new ArrayList<>();
	    
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSelectAllSql();
			stm 	   = connection.prepareStatement(sql);
			rs 		   = stm.executeQuery();
			while(rs.next()) {
				client  = new Client();
				Long id = rs.getLong("cd_cliente");
				String name = rs.getString("nm_cliente");
				Long cpf 	= rs.getLong("cpf");
				client.setId(id);
				client.setCpf(cpf);
				client.setName(name);
				list.add(client);
				
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		return list;
	}


	private String getSelectAllSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM CLIENTE");
		return sb.toString();
	}

}
