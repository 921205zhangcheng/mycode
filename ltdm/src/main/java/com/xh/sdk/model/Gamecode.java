package com.xh.sdk.model;

import java.util.List;

public class Gamecode {
	private int id;
	private String gName;
	private String smsContent;
	private String smsPort;

	private String smsContentA;
	private String smsPortA;

	private String isblock;
	private String pbPort;
	private String pbContent;
	private int cost;
	private String payType;
	private String orderId;
	private String pbPortA;
	private String smsSite;
	private String re;
	private List actionList;

	public String getPayType() {
		return payType;
	}

	
	public String getSmsSite() {
		return smsSite;
	}


	public List getActionList() {
		return actionList;
	}


	public void setActionList(List actionList) {
		this.actionList = actionList;
	}


	public void setSmsSite(String smsSite) {
		this.smsSite = smsSite;
	}


	public String getRe() {
		return re;
	}
	

	public String getPbPortA() {
		return pbPortA;
	}

	public void setPbPortA(String pbPortA) {
		this.pbPortA = pbPortA;
	}

	public String getSmsContentA() {
		return smsContentA;
	}

	public void setSmsContentA(String smsContentA) {
		this.smsContentA = smsContentA;
	}

	public String getSmsPortA() {
		return smsPortA;
	}

	public void setSmsPortA(String smsPortA) {
		this.smsPortA = smsPortA;
	}

	public void setRe(String re) {
		this.re = re;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getSmsPort() {
		return smsPort;
	}

	public void setSmsPort(String smsPort) {
		this.smsPort = smsPort;
	}

	public String getIsblock() {
		return isblock;
	}

	public void setIsblock(String isblock) {
		this.isblock = isblock;
	}

	public String getPbPort() {
		return pbPort;
	}

	public void setPbPort(String pbPort) {
		this.pbPort = pbPort;
	}

	public String getPbContent() {
		return pbContent;
	}

	public void setPbContent(String pbContent) {
		this.pbContent = pbContent;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}