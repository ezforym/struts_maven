package com.wyz.pojo;

public class User {
	private String address;
	private Integer id;
	private String name;
	private String phone;
	private boolean dr;

	public void setAddress(String address) {
		this.address = address;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setDr(boolean dr) {
		this.dr = dr;
	}

	public String getAddress() {
		return address;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public boolean getDr() {
		return dr;
	}
}
