package com.jain.schl.svcstdntdtl.model;

import java.util.Map;

import lombok.Data;

@Data
public class ResponseMetaData {
	private long responseTime;
	private int statusCode;
	private String message;
	private ErrorDetails errorDetails;
	private Map<String, Object> metadata;
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public ErrorDetails getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(ErrorDetails errorDetails) {
		this.errorDetails = errorDetails;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return "ResponseMetaData [responseTime=" + responseTime + ", statusCode=" + statusCode + ", errorDetails="
				+ errorDetails + ", metadata=" + metadata + "]";
	};;;
	
}
