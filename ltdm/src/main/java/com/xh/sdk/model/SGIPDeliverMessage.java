package com.xh.sdk.model;

public class SGIPDeliverMessage {
	
	private String SPNumber;

	private String UserNumber;

	private String Reserve;
	
	private byte[] MsgContent;
	
	private int CommandId;
	
	private int Fmt;
	
	private int MsgLength;
	
	private int TpPid;
	
	private int TpUdhi;

	public String getSPNumber() {
		return SPNumber;
	}

	public void setSPNumber(String sPNumber) {
		SPNumber = sPNumber;
	}

	public String getUserNumber() {
		return UserNumber;
	}

	public void setUserNumber(String userNumber) {
		UserNumber = userNumber;
	}

	public String getReserve() {
		return Reserve;
	}

	public void setReserve(String reserve) {
		Reserve = reserve;
	}

	public byte[] getMsgContent() {
		return MsgContent;
	}

	public void setMsgContent(byte[] msgContent) {
		MsgContent = msgContent;
	}

	public int getCommandId() {
		return CommandId;
	}

	public void setCommandId(int commandId) {
		CommandId = commandId;
	}

	public int getFmt() {
		return Fmt;
	}

	public void setFmt(int fmt) {
		Fmt = fmt;
	}

	public int getMsgLength() {
		return MsgLength;
	}

	public void setMsgLength(int msgLength) {
		MsgLength = msgLength;
	}

	public int getTpPid() {
		return TpPid;
	}

	public void setTpPid(int tpPid) {
		TpPid = tpPid;
	}

	public int getTpUdhi() {
		return TpUdhi;
	}

	public void setTpUdhi(int tpUdhi) {
		TpUdhi = tpUdhi;
	}
	
	
	
}
