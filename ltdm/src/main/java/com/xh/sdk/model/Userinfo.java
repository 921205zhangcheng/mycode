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
@Table(name = "user_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value = true)
@DynamicUpdate(value = true)

public class Userinfo {
	@Id
	@Column(name = "id")
	@GeneratedValue()
	private Integer id;
	@Column(name = "phone")
	private String phone;
	@Column(name = "ua")
	private String ua;
	@Column(name = "ip")
	private String ip;
	@Column(name = "imei")
	private String imei;
	@Column(name = "imsi")
	private String imsi;
	@Column(name = "appId")
	private String appId;
	@Column(name = "iccid")
	private String iccid;
	@Column(name = "productId")
	private String productId;
	@Column(name = "cpid")
	private String cpid;
	@Column(name = "netType")
	private String netType;
	@Column(name = "bagName")
	private String bagName;
	@Column(name = "userType")
	private String userType;
	@Column(name = "addtime", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;
	@Column(name = "PhoneModel")
	private String PhoneModel;
	@Column(name = "lac")
	private String lac;
	@Column(name = "cid")
	private String cid;
	@Column(name = "PhoneSdkVersion")
	private String PhoneSdkVersion;
	@Column(name = "PhoneClipType")
	private String PhoneClipType;
	@Column(name = "version")
	private String version;
	@Column(name = "sdkversion")
	private String sdkversion;
	
	private String tableName;

	private String yys;
	private String province;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getYys() {
		return yys;
	}

	public void setYys(String yys) {
		this.yys = yys;
	}

	public Integer getId() {
		return this.id;
	}

	public String getUa() {
		return ua;
	}

	


	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	public String getLac() {
		return lac;
	}

	public void setLac(String lac) {
		this.lac = lac;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImei() {
		return this.imei;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return this.imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

	public String getNetType() {
		return this.netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getBagName() {
		return this.bagName;
	}

	public void setBagName(String bagName) {
		this.bagName = bagName;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getPhoneModel() {
		return PhoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		PhoneModel = phoneModel;
	}

	public String getPhoneSdkVersion() {
		return PhoneSdkVersion;
	}

	public void setPhoneSdkVersion(String phoneSdkVersion) {
		PhoneSdkVersion = phoneSdkVersion;
	}

	public String getPhoneClipType() {
		return PhoneClipType;
	}

	public void setPhoneClipType(String phoneClipType) {
		PhoneClipType = phoneClipType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSdkversion() {
		return sdkversion;
	}

	public void setSdkversion(String sdkversion) {
		this.sdkversion = sdkversion;
	}



}