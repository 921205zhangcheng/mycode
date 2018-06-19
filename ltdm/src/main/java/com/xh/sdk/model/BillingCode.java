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
@Table(name = "billing_code")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
public class BillingCode {
	@Id
	@Column(name = "id")
	@GeneratedValue()
	private int id;

	@Column(name = "Name")
	private String Name;

	@Column(name = "type")
	private String type;

	@Column(name = "yystype")
	private String yystype;

	@Column(name = "pronvice")
	private String pronvice;

	@Column(name = "level")
	private String level;

	//

	@Column(name = "consumecode")
	private String consumecode;

	@Column(name = "price")
	private String price;

	@Column(name = "codeType")
	private String codeType;
	
	@Column(name = "limited")
	private String limit;

	@Column(name = "mlimited")
	private String mlimit;

	@Column(name = "isyong")
	private String isyong;
	
	//是否分时段
	private int isDayparting;
	//开启时间
	private String opentime;
	//关闭时间
	private String closetime;
	
	public int getIsDayparting() {
		return isDayparting;
	}

	public void setIsDayparting(int isDayparting) {
		this.isDayparting = isDayparting;
	}

	public String getOpentime() {
		return opentime;
	}

	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}

	public String getClosetime() {
		return closetime;
	}

	public void setClosetime(String closetime) {
		this.closetime = closetime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYystype() {
		return yystype;
	}

	public void setYystype(String yystype) {
		this.yystype = yystype;
	}

	public String getLimit() {
		return limit;
	}


	public String getMlimit() {
		return mlimit;
	}

	public void setMlimit(String mlimit) {
		this.mlimit = mlimit;
	}

	public String getConsumecode() {
		return consumecode;
	}



	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public void setConsumecode(String consumecode) {
		this.consumecode = consumecode;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getIsyong() {
		return isyong;
	}

	public void setIsyong(String isyong) {
		this.isyong = isyong;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPronvice() {
		return pronvice;
	}

	public void setPronvice(String pronvice) {
		this.pronvice = pronvice;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
