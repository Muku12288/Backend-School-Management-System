package com.school.dto;

public class AuthenticationResponse {
	private String jwtToken;

	public AuthenticationResponse(String jwt) {
		// TODO Auto-generated constructor stub
		setJwtToken(jwt);
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}
