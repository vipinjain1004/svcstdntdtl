package com.jain.schl.svcstdntdtl.model;

import lombok.Data;

@Data
public class ErrorDetails {
	private String errorCode;
	private String internalMessage;
	private String clientMessage;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getInternalMessage() {
		return internalMessage;
	}

	public void setInternalMessage(String internalMessage) {
		this.internalMessage = internalMessage;
	}

	public String getClientMessage() {
		return clientMessage;
	}

	public void setClientMessage(String clientMessage) {
		this.clientMessage = clientMessage;
	}

	@Override
	public String toString() {
		return "ErrorDetails [errorCode=" + errorCode + ", internalMessage=" + internalMessage + ", clientMessage="
				+ clientMessage + "]";
	}
	
}
