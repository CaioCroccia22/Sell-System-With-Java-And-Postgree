package br.com.ccroccia.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.com.ccroccia.dao.generics.GenericDAO;
import br.com.ccroccia.domain.Product;

public class ProductDAO extends GenericDAO<Product, Long> implements IProductDao {

	public ProductDAO() {
		super();
	}

	@Override
	public Class<Product> getTypeClass() {
		return Product.class;
	}

	/// ============= INSERT =========================
	@Override
	protected String getQueryInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO Product ");
		sb.append("(cd_product, nm_product, ds_product, vl_price, qtd_product) ");
		sb.append("VALUES (nextval('sq_product'), ?, ?, ?, ?)");

		return sb.toString();
	}

	@Override
	protected void setParametersInsert(PreparedStatement stm, Product p) throws SQLException {
		stm.setString(1, p.getName());
		stm.setString(2, p.getDescription());
		stm.setDouble(3, p.getPrice());
		stm.setInt(4, p.getQuantity());
	}

	/// ============= DELETE =========================
	@Override
	protected String getQueryDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM Product ");
		return sb.toString();
	}

	@Override
	protected void setParametersDelete(PreparedStatement stm, Long value) throws SQLException {
		stm.setLong(1, value);
	}

	/// ============= UPDATE =========================
	@Override
	protected String getQueryUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE Product ");
		sb.append("SET nm_product = ?, ds_product = ?, vl_price = ?, qtd_product = ? ");
		return sb.toString();
	}

	@Override
	protected void setParametersUpdate(PreparedStatement stm, Long value) throws SQLException {
		stm.setLong(1, value);
	}

	/// ============= SELECT =========================
	@Override
	protected void setParametersSelect(PreparedStatement stm, Long value) throws SQLException {
		stm.setLong(1, value);
	}


}
