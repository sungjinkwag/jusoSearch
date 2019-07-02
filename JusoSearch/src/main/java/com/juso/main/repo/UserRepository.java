package com.juso.main.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.juso.main.entity.LoginUserInfo;

public interface UserRepository extends CrudRepository<LoginUserInfo, String> {
	Optional<LoginUserInfo> findById(String id);
}
