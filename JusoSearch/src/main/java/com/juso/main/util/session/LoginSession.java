package com.juso.main.util.session;

import java.util.HashMap;

import com.juso.main.dto.UserInfo;
import com.juso.main.entity.LoginUserInfo;

public class LoginSession {
	// 로그인 사용자 정보
	private UserInfo userInfo;
	// 로그인 상태에서 임시로 사용 할 tempMap
	@SuppressWarnings("rawtypes")
	private HashMap tempDataMap;
	
	@SuppressWarnings("rawtypes")
	public LoginSession() {
		userInfo =  new UserInfo();
		tempDataMap =  new HashMap();
	}
	
	/**
	 * 로그인 UserInfo set
	 */
	public void setUserInfo(LoginUserInfo loginUserInfo) {
		if(userInfo == null){
			userInfo =  new UserInfo();
		} else {
			userInfo.setId(loginUserInfo.getId());
		}
	}

	/**
	 * 로그인 UserInfo get
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * 로그인 세션 임시값 저장
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void putTempValue(String key, Object value){
		if(tempDataMap == null){
			tempDataMap = new HashMap();
		}
		tempDataMap.put(key, value);
	}
	
	/**
	 * 로그인 세션 임시값 꺼내기
	 */
	@SuppressWarnings("rawtypes")
	public Object getTempValue(String key){
		if(tempDataMap == null){
			tempDataMap = new HashMap();
		}
		return tempDataMap.get(key);
	}
	
	/**
	 * 로그인 세션 임시값 꺼내기
	 */
	@SuppressWarnings("rawtypes")
	public String getTempToString(){
		if(tempDataMap == null){
			tempDataMap = new HashMap();
		}
		return tempDataMap.toString();
	}
	
	/**
	 * 로그인 세션 임시값 지우기
	 */
	public void clearTempMap(){
		tempDataMap.clear();
		tempDataMap = null;
	}
}
