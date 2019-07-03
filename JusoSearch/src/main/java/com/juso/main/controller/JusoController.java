package com.juso.main.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juso.main.entity.MyHistKeyword;
import com.juso.main.entity.PopKeyword;
import com.juso.main.service.KeywordService;
import com.juso.main.util.session.LoginSession;
import com.juso.main.util.session.SessionManager;

@Controller
public class JusoController {
	
	LoginSession loginSession;
	String id;
	
	@Autowired
	KeywordService keywordService;
	
	@GetMapping("/searchJuso")
	public ModelAndView goSearchJuso(HttpServletRequest request) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView("searchJuso");
		loginSession = SessionManager.getLoginSession(request);
		id = loginSession.getUserInfo().getId();
		
		// 내 검색 키워드 목록 불러오기
		List<MyHistKeyword> myKeyList = keywordService.getMyKeyList(id);
		mv.addObject("myHistKeyList", myKeyList);
		// 인기 검색 키워드 목록 불어오기
		List<PopKeyword> popKeyList = keywordService.getPopKeyList();
		mv.addObject("popKeywordList", popKeyList);
		return mv;
	}
	
	@ResponseBody
	@GetMapping("/myKeyList/get")
	public String getMyKeyList(HttpServletRequest request) throws JsonProcessingException {
		loginSession = SessionManager.getLoginSession(request);
		id = loginSession.getUserInfo().getId();
		String result = "";
		
		// 내 검색 키워드 목록 불러오기
		List<MyHistKeyword> myKeyList = keywordService.getMyKeyList(id);
		ObjectMapper om = new ObjectMapper();
		result = om.writeValueAsString(myKeyList);
		return result;
	}
	
	@ResponseBody
	@GetMapping("/popKeyList/get")
	public String getPopKeyList(HttpServletRequest request) throws JsonProcessingException {
		String result = "";
		
		// 인기 검색 키워드 목록 불어오기
		List<PopKeyword> popKeyList = keywordService.getPopKeyList();
		ObjectMapper om = new ObjectMapper();
		result = om.writeValueAsString(popKeyList);
		return result;
	}	
	
	@ResponseBody
	@GetMapping("/searchKeyword")
	public String searchKeyword(HttpServletRequest request, @RequestParam Map<String, Object> paraMap) {
		String keyword = (String)paraMap.get("keyword");
		String page = (String)paraMap.get("currentPage");
		loginSession = SessionManager.getLoginSession(request);
		id = loginSession.getUserInfo().getId();
		// 검색한 키워드 내 검색리스트에 저장
		MyHistKeyword saveKeyword = new MyHistKeyword(id, keyword, LocalDateTime.now());
		keywordService.myKeySave(saveKeyword);
		// 검색한 키워드 인기 검색 리스트에 저장
		if(keywordService.popKeyExistYn(keyword)) {
			PopKeyword tempPop = keywordService.getKeyword(keyword);
			int tempCnt = tempPop.getCnt();
			tempCnt += 1;
			tempPop.setCnt(tempCnt);
			keywordService.popKeySave(tempPop);
		} else {
			keywordService.popKeySave(new PopKeyword(keyword, 1));
		}
		
		if(page == null || page == "") {
			page = "1";
		}
		
		String result = "";
		try{
			
			URL url = new URL("https://dapi.kakao.com/v2/local/search/keyword.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Authorization", "KakaoAK 2487432647e5d8fc1524c381c0ba91fc");
			conn.setDoOutput(true);
			
			String input = "query="+keyword+"&size=10&page="+page;
			OutputStream os = conn.getOutputStream();	    
			
			os.write(input.getBytes("UTF-8"));
			os.flush();
			os.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			result = response.toString();
		}catch(Exception e){
			System.out.println(e);
		}		
		return result;
	}
}
