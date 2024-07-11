package com.example.ecommerce.books.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResp 
{
	protected String error;
	
	public ErrorResp(@JsonProperty("error") String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
