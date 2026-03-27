package br.com.ccroccia.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.ccroccia.dao.generics.GenericDAO;
import br.com.ccroccia.dao.generics.IGenericDAO;
import br.com.ccroccia.domain.Client;
import br.com.ccroccia.exceptions.KeyTypeNotFoundException;
import br.com.ccrocia.dao.generic.jdbc.ConnectionFactory;

public class ClientDAO extends GenericDAO<Client, Long> implements IClientDao {
	
	public ClientDAO() {
		super();
	}

	/// ============= insert =========================
	@Override
	protected String getQueryInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO CLIENTE ");
		sb.append("(cd_client, nm_client, cd_cpf, nr_age, nr_phone, ds_address, nr_adress_number, ds_city, ds_state) ");
		sb.append("VALUES (nextval('sq_cliente'), ?, ?, ?, ?, ?, ?, ?, ?)");
		return sb.toString();
	}
	
	@Override
	protected void setParametersInsert(PreparedStatement stm, Client c) throws SQLException {
		stm.setString(1, c.getName());
		stm.setLong(2, c.getCpf());
		stm.setString(3, c.getAge());
		stm.setLong(4, c.getPhone());
		stm.setString(5, c.getAddress());
		stm.setInt(6, c.getNumber());
		stm.setString(7, c.getCity());
		stm.setString(8, c.getState());
	}
	
	///=============== delete =============================
	@Override
	protected String getQueryDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM CLIENTE ");
		return sb.toString();
	}
	
	@Override
	protected void setParametersDelete(PreparedStatement stm, Long value) throws SQLException {
		stm.setLong(1, value);
	}
	
	
	/// ========================= UPDATE ==================================
	@Override
	protected String getQueryUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE "
				+ "	SET  cd_cpf = 3232323232L"
				+ " FROM		CLIENTE ");
		return sb.toString();
	}
	//In that first moment I fixed the set value to update
	
	@Override
	protected void setParametersUpdate(PreparedStatement stm, Long value) throws SQLException {
		stm.setLong(1, value);
	}

	
	// ============================== SELECT ===========================================
	@Override
	protected void setParametersSelect(PreparedStatement stm, Long value) throws SQLException {
		stm.setLong(1, value);
	}




	
	// ==========================================================================

	@Override
	public Class<Client> getTypeClass() {
		return Client.class;
	}

}
