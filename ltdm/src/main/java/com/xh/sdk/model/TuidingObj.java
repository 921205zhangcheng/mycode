package com.xh.sdk.model;

import java.util.Date;



public class TuidingObj {

	private int id;

	private String productId;

	private String phone;
	
	private String appId;
	
	private String statu;
	
	private Date dgtime;
	
	private Date addtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Date getDgtime() {
		return dgtime;
	}

	public void setDgtime(Date dgtime) {
		this.dgtime = dgtime;
	}






}

