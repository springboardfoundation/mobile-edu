package org.freesource.mobedu.utils;

@SuppressWarnings("serial")
public class MobileEduException extends Exception {

	/**
	 * Class for catching custom exceptions for Specific Error code and Error
	 * Message
	 * 
	 */
	public String errorCode;
	public String errorMsg;

	public static final String ENTITY_NOT_FOUND = "ENTITY_NOT_FOUND";
	public static final String ENTITY_NOT_FOUND_MSG = "DB Entry doesn't Exists!!!";

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public MobileEduException(String errCode, String msg) {
		super(msg);
		errorCode = errCode;
		errorMsg = msg;
	}

	public MobileEduException(String msg) {
		super(msg);
		errorMsg = msg;
	}

	public MobileEduException(String msg, Throwable e) {
		super(msg, e);
		errorMsg = msg;
	}

	@Override
	public String toString() {
		return "MobileEdu - Exception [Erro Code=" + errorCode
				+ ", Error Message=" + errorMsg + "]";
	}
}