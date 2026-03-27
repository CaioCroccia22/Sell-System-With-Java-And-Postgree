package br.com.ccroccia.domain;

import annotation.*;
import br.com.ccroccia.dao.Persistent;

@Table(tableName = "Product")
public class Product implements Persistent {

	@KeyType("getId")
	@Column(columnName = "cd_product", method = "setId")
	private Long id;

	@Column(columnName = "nm_product", method = "setName")
	private String name;

	@Column(columnName = "ds_product", method = "setDescription")
	private String description;

	@Column(columnName = "vl_price", method = "setPrice")
	private Double price;

	@Column(columnName = "qtd_product", method = "setQuantity")
	private Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
