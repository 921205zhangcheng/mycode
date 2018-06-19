package com.xh.sdk.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "pay_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
public class Payinfo {
	@Id
	@Column(name = "id")
	@GeneratedValue()
	private Integer id;

	@Column(name = "imei")
	private String imei;

	@Column(name = "imsi")
	private String imsi;

	@Column(name = "iccid")
	private String iccid;

	@Column(name = "ip")
	private String ip;

	@Column(name = "lac")
	private String bsc_lac;
	@Column(name = "cid")
	private String bsc_cid;

	@Column(name = "orderId")
	private String orderId;

	@Column(name = "payType")
	private String payType;

	@Column(name = "cpparam")
	private String cpparam;
	
	

	@Column(name = "phone")
	private String phone;
	

	@Column(name = "addtime", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	@Column(name = "smsresult")
	private String smsresult;

	@Column(name = "result")
	private String result;
	// result 状态说明
	// 0 计费成功
	// 1 发送短信成功
	// 2下发验证短信成功
	// 3提交 短信验证码成功

	@Column(name = "resultDesc")
	private String resultDesc;



	@Column(name = "appId")
	private String appId;

	@Column(name = "ua")
	private String ua;

	@Column(name = "province")
	private String province;

	@Column(name = "re")
	private String re;

	@Column(name = "productId")
	private String productId;

	@Column(name = "cost")
	private int cost;

	@Column(name = "payId")
	private String payId;

	@Column(name = "sycstatu")
	private String sycstatu;
	
	private String tableName;
	




	public String getSycstatu() {
		return sycstatu;
	}

	public void setSycstatu(String sycstatu) {
		this.sycstatu = sycstatu;
	}

	public Integer getId() {
		return this.id;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	public String getBsc_lac() {
		return bsc_lac;
	}


	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCpparam() {
		return cpparam;
	}

	public void setCpparam(String cpparam) {
		this.cpparam = cpparam;
	}

	public void setBsc_lac(String bsc_lac) {
		this.bsc_lac = bsc_lac;
	}

	public String getBsc_cid() {
		return bsc_cid;
	}

	public void setBsc_cid(String bsc_cid) {
		this.bsc_cid = bsc_cid;
	}
	public String getIccid() {
		return iccid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getPhone() {
		return phone;
	}

	public String getRe() {
		return re;
	}

	public void setRe(String re) {
		this.re = re;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getSmsresult() {
		return smsresult;
	}

	public void setSmsresult(String smsresult) {
		this.smsresult = smsresult;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}



	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
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

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

}