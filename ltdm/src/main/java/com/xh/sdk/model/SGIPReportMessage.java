package com.xh.sdk.model;

public class SGIPReportMessage {

	private int State;
	private int ErrorCode;

	private int ReportType;
	private String UserNumber;

	private String Reserve;

	private int CommandId;

	private byte[] SubmitSequenceNumber;

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public int getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(int errorCode) {
		ErrorCode = errorCode;
	}

	public int getReportType() {
		return ReportType;
	}

	public void setReportType(int reportType) {
		ReportType = reportType;
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

	public int getCommandId() {
		return CommandId;
	}

	public void setCommandId(int commandId) {
		CommandId = commandId;
	}

	public byte[] getSubmitSequenceNumber() {
		return SubmitSequenceNumber;
	}

	public void setSubmitSequenceNumber(byte[] submitSequenceNumber) {
		SubmitSequenceNumber = submitSequenceNumber;
	}

}
