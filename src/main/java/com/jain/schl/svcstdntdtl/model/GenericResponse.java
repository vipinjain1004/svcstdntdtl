package com.jain.schl.svcstdntdtl.model;

import lombok.Data;

@Data
public class GenericResponse<T> {
	private T responseBody;
	private ResponseMetaData responseMetaData;
	private Pagination pagination;
	
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public T getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(T responseBody) {
		this.responseBody = responseBody;
	}

	public ResponseMetaData getResponseMetaData() {
		return responseMetaData;
	}

	public void setResponseMetaData(ResponseMetaData responseMetaData) {
		this.responseMetaData = responseMetaData;
	}

	@Override
	public String toString() {
		return "GenericResponse [responseBody=" + responseBody + ", responseMetaData=" + responseMetaData
				+ ", pagination=" + pagination + "]";
	}


}
