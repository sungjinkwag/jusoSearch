package com.juso.main.dto;

public class UserInfo {
	private String id;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + "]";
	}
	
	
}
