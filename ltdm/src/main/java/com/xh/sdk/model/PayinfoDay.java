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
@Table(name = "ltdm_pay_info_day")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
public class PayinfoDay {
	@Id
	@Column(name = "id")
	@GeneratedValue()
	private Integer id;
	@Column(name = "cpparam")
	private String cpparam;

	@Column(name = "addtime")
	private String addtime;


	@Column(name = "result")
	private String result;
	// result 状态说明
	// 0 计费成功
	// 1 发送短信成功
	// 2下发验证短信成功
	// 3提交 短信验证码成功
	@Column(name = "province")
	private String province;

	@Column(name = "productId")
	private String productId;

	@Column(name = "costsum")
	private int costsum;
	
	private String tableName;
	@Column(name = "payType")
	private int payType;
	@Column(name = "companyId")
	private int companyId;



	public Integer getId() {
		return id;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getCostsum() {
		return costsum;
	}

	public void setCostsum(int costsum) {
		this.costsum = costsum;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	

}