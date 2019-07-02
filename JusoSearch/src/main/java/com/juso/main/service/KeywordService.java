package com.juso.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juso.main.entity.MyHistKeyword;
import com.juso.main.entity.PopKeyword;
import com.juso.main.repo.MyKeywordRepository;
import com.juso.main.repo.PopKeywordRepository;

@Service
public class KeywordService {

	@Autowired
	MyKeywordRepository myKeyRepo;
	
	@Autowired
	PopKeywordRepository popKeyRepo;
	
	public MyHistKeyword myKeySave(MyHistKeyword myKeyword) {
		return myKeyRepo.save(myKeyword);
	}

	public boolean popKeyExistYn(String keyword) {
		return popKeyRepo.existsById(keyword);
	}

	public PopKeyword getKeyword(String keyword) {
		return popKeyRepo.findById(keyword).get();
	}

	public PopKeyword popKeySave(PopKeyword popKeyword) {
		return popKeyRepo.save(popKeyword);
	}

	public List<PopKeyword> getPopKeyList() {
		return popKeyRepo.findTop10ByOrderByCntDesc();
	}
	
	public List<MyHistKeyword> getMyKeyList(String id) {
		return myKeyRepo.findTop10ByIdOrderByRegTimeDesc(id);
	}


}
