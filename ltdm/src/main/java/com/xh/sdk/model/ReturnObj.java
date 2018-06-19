package com.xh.sdk.model;

public class ReturnObj {

	public static String NO_CHANNEL = "1001";
	
	public static String NO_CHANNEL_MSG = "渠道或者代码不存在";
	
	public static String BLACK_PHONE = "2001";
	
	public static String BLACK_PHONE_MSG = "号码在黑名单";
	
	
	public static String YYS_PHONE = "2002";
	
	public static String YYS_PHONE_MSG = "运营商不正确";
	
	
	public static String PROV_BLOCK = "2003";
	
	public static String PROV_BLOCK_MSG = "省份屏蔽";
	
	public static String CODE_LIMIT = "2004";
	
	public static String CODE_LIMIT_MSG = "代码限量";
	
	public static String NO_CODE = "2005";
	
	public static String NO_CODE_MSG = "代码暫停";
	
	public static String UNKNOW_ERROR = "9999";
	
	public static String UNKNOW_ERROR_MSG = "未知错误";
	
	
	public static String T_RESULT= "3001";
	
	public static String T_RESULT_MSG = "结果不存在";
	
	private String statu;
	
	private String errorMsg;
	
	private String orderId;
	
	private String t_result;
	
	private Gamecode code;

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	
	public String getOrderId() {
		return orderId;
	}
	
	

	public String getT_result() {
		return t_result;
	}

	public void setT_result(String t_result) {
		this.t_result = t_result;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Gamecode getCode() {
		return code;
	}

	public void setCode(Gamecode code) {
		this.code = code;
	}

}
