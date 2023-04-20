package com.jain.schl.svcstdntdtl.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String userName;
	

	public JwtResponse(String jwttoken, String userName) {
		this.jwttoken = jwttoken;
		this.userName = userName;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public String getUserName() {
		return this.userName;
				}
}