package com.juso.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoginUserInfo {
	@Id
	@Column(name="ID", length = 100, nullable = true, unique = true)
	private String id;
	
	@Column(name="PW", length = 500, nullable = true)
	private String pw;
	
	public LoginUserInfo() {}
	
	public LoginUserInfo(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

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

	/**
	 * @return the pw
	 */
	public String getPw() {
		return pw;
	}

	/**
	 * @param pw the pw to set
	 */
	public void setPw(String pw) {
		this.pw = pw;
	}

	@Override
	public String toString() {
		return "LoginUserInfo [id=" + id + ", pw=" + pw + "]";
	}
	
}
