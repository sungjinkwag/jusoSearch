package com.juso.main.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(MyHistKeys.class)
public class MyHistKeyword {

	@Id
	@Column(name="ID", length = 100, nullable = true)
	private String id;

	@Id
	@Column(name="KEYWORD", length = 500, nullable = true)
	private String keyword;
	
	@Column(name="REGTIME", length = 20, nullable = true)
	private LocalDateTime regTime;
	
	public MyHistKeyword() {}

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
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the regTime
	 */
	public LocalDateTime getRegTime() {
		return regTime;
	}

	/**
	 * @param regTime the regTime to set
	 */
	public void setRegTime(LocalDateTime regTime) {
		this.regTime = regTime;
	}

	@Override
	public String toString() {
		return "MyHistKeyword [id=" + id + ", keyword=" + keyword + ", regTime=" + regTime + "]";
	}

	public MyHistKeyword(String id, String keyword, LocalDateTime regTime) {
		super();
		this.id = id;
		this.keyword = keyword;
		this.regTime = regTime;
	}
	
}
