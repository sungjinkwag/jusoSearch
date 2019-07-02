package com.juso.main.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juso.main.entity.LoginUserInfo;
import com.juso.main.service.LoginService;
import com.juso.main.util.enc.GenerateEnc;
import com.juso.main.util.session.LoginSession;
import com.juso.main.util.session.SessionManager;

@Controller
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@GetMapping("/login")
	public String goLogin() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String goLogout(HttpServletRequest request) {
		SessionManager.executeLogout(request);
		return "redirect:/";
	}
	
	@ResponseBody
	@GetMapping("/login/do")
	public boolean checkPw(HttpServletRequest request, @RequestParam Map<String, Object> paraMap) {
		
		boolean result = false;
		LoginUserInfo userInfo = null;

		// 비밀번호 검증
		String id = (String) paraMap.get("id");
		try {
			userInfo = loginService.pwGet(id);
			String encPw = GenerateEnc.getEnc((String)paraMap.get("pw"));
			if(encPw != null) {
				if(encPw.equals(userInfo.getPw())) {
					result = true;
				} else {
					result = false;
				}
			}
		} catch (NoSuchElementException e) {
			result = false;
		}
		
		// 로그인 정보가 일치하면 세션을 생성함
		if(result && userInfo != null) {
			try {
				LoginSession loginSession = new LoginSession();
				SessionManager.createLoginSession(request, userInfo, loginSession);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
