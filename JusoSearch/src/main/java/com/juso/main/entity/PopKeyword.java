package com.juso.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PopKeyword {

	@Id
	@Column(name="KEYWORD", length = 100, nullable = true, unique = true)
	private String keyword;
	
	@Column(name="CNT", length = 3, nullable = true)
	private int cnt;

	public PopKeyword() {}
	
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
	 * @return the cnt
	 */
	public int getCnt() {
		return cnt;
	}

	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public PopKeyword(String keyword, int cnt) {
		super();
		this.keyword = keyword;
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "PopKeyword [keyword=" + keyword + ", cnt=" + cnt + "]";
	}
	
}
