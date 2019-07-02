package com.juso.main.util.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.juso.main.entity.LoginUserInfo;


public class SessionManager {
	
	public SessionManager() {
	}
	
	public static LoginSession getLoginSession(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		return (LoginSession) session.getAttribute("loginSession");
	}


	/**
	 * 로그인 여부 확인
	 * @param request
	 * @return 
	 */
	public static boolean isLogin(HttpServletRequest request) {
		if (getLoginSession(request) == null){
			return false;
		} else {
			return true;
		}
	}


	/**
	 * 로그아웃
	 * @param request
	 */
	public static void executeLogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginSession");
		session.invalidate();
	}

	/**
	 * 비로그인 상태로 요청했던 url을 알려 줍니다.
	 * 
	 * @param session
	 */
	public static String getTargetUrl(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String url = (String) session.getAttribute("TargetUrl");
		if (url == null)
			return null;
		return url;
	}
    
	/**
	 * 
	 * @param request
	 * @param userInfo
	 * @param loginSession
	 * @throws Exception
	 */
	public static void createLoginSession(HttpServletRequest request, LoginUserInfo userInfo, LoginSession loginSession) throws Exception {
		setSessionTime(request);
		loginSession.setUserInfo(userInfo);
		HttpSession session = request.getSession();
		session.setAttribute("loginSession", loginSession);
	}
	
    /**
     * 
     * @param request
     * @throws Exception
     */
 	public static void setSessionTime(HttpServletRequest request) throws Exception {
 		int sessionTimeout = 15;
 		sessionTimeout *= 60;
 		request.getSession().setMaxInactiveInterval(sessionTimeout);
 	}
}
