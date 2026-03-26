package br.com.ccroccia.domain;

import annotation.Column;
import annotation.Table;

@Table(tableName = "Product")
public class Product {

	@Column(columnName = "cd_produto", method = "setId")
	private Integer id;
	@Column(columnName = "ds_produto", method = "setDescription")
	private String description;
	@Column(columnName = "qtd_produto", method = "setQuantity")
	private Integer quantity;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
