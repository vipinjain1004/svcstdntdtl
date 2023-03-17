package com.jain.schl.svcstdntdtl.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jain.schl.svcstdntdtl.model.ErrorDetails;
import com.jain.schl.svcstdntdtl.model.GenericResponse;
import com.jain.schl.svcstdntdtl.model.ResponseMetaData;

@ControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomErrorHandler extends RuntimeException {
	private String message;
	private String clientMessage;
	private int status;
	public CustomErrorHandler(String message, String clientMessage, int status) {
		super(message);
		this.message= message;
		this.clientMessage = clientMessage;
		this.status = status;		
	}
	public CustomErrorHandler() {
		super();			
		
	}
	
}
