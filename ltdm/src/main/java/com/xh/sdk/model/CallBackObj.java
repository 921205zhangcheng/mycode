package com.xh.sdk.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "pay_foryd")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
public class CallBackObj {

	private int id;

	private String orderId;

	private Date addtime;

	private String appId;

	private String productId;

	private int cost;
	
	private String imsi;
	
	private String imei;
	
	private String phone;
	
	private String cpparam;
	
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getPhone() {
		return phone;
	}
	
	
	public String getCpparam() {
		return cpparam;
	}
	public void setCpparam(String cpparam) {
		this.cpparam = cpparam;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	
}
