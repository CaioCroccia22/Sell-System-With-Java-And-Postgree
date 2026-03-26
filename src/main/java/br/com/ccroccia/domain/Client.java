package br.com.ccroccia.domain;


import annotation.*;

import br.com.ccroccia.dao.Persistent;

@Table(tableName = "Cliente")
public class Client implements Persistent{

	
	@Column(columnName = "cd_client", method = "setId")
	private Long id;
	@KeyType("getCpf")
	@Column(columnName = "cd_cpf", method = "setCpf")
	private Long cpf;
	@Column(columnName = "nm_client", method = "setName")
	private String name;
	@Column(columnName = "nr_age", method = "setAge")
	private String age;
	@Column(columnName = "nr_phone", method = "setPhone")
	private Long phone;
	@Column(columnName = "ds_address", method = "setAddress")
	private String address;
	@Column(columnName = "nr_adress_number", method = "setNumber")
	private Integer number;
	@Column(columnName = "ds_city", method = "setCity")
	private String city;
	@Column(columnName = "ds_state", method = "setState")
	private String state;


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
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

}
