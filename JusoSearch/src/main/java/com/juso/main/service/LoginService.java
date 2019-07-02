package com.juso.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juso.main.entity.LoginUserInfo;
import com.juso.main.repo.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	UserRepository userRepo;
	
	public LoginUserInfo pwGet(String id) {
		return userRepo.findById(id).get();
	}
}
