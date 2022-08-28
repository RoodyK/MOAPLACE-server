package com.moaplace.authentication;

public enum AuthorizationType {
	BASIC,
	BEARER;
	
	public String thisName() {
		return this.name().substring(0, 1).toUpperCase() + 
				this.name().substring(1).toLowerCase();
	}
}
