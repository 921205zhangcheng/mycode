package com.xh.sdk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "dx_prov_manage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
public class DxprovCode {

	@Id
	@Column(name = "id")
	@GeneratedValue()
	private int id;

	@Column(name = "prov")
	private String prov;

	@Column(name = "provcode")
	private String provcode;

	@Column(name = "city")
	private String city;

	@Column(name = "citycode")
	private String citycode;

	@Column(name = "provid")
	private String provid;
	

	@Column(name = "jiancheng")
	private String jiancheng;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getProvcode() {
		return provcode;
	}

	public String getJiancheng() {
		return jiancheng;
	}

	public void setJiancheng(String jiancheng) {
		this.jiancheng = jiancheng;
	}

	public void setProvcode(String provcode) {
		this.provcode = provcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getProvid() {
		return provid;
	}

	public void setProvid(String provid) {
		this.provid = provid;
	}

	
	
}
