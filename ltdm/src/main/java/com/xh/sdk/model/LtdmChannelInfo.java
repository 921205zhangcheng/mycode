package com.xh.sdk.model;

public class LtdmChannelInfo {

	
	private int id;

	private String companyname;


	private String companyId;


	private String wLimit;
	
	private String secretkey;

	private String synUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getwLimit() {
		return wLimit;
	}

	public void setwLimit(String wLimit) {
		this.wLimit = wLimit;
	}

	public String getSynUrl() {
		return synUrl;
	}

	public void setSynUrl(String synUrl) {
		this.synUrl = synUrl;
	}

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

}
