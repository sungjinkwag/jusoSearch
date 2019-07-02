package com.juso.main.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.juso.main.constants.JusoConstants;
import com.juso.main.util.session.SessionManager;

@Controller
public class StartController {

	@GetMapping("/")
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(JusoConstants.MASTER); 
		if(SessionManager.isLogin(request)) {
			mv.addObject("isLogin","Y");
		} else {
			mv.addObject("isLogin","N");
		}
		return mv;
	}
}
