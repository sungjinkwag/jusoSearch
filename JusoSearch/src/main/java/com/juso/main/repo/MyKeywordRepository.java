package com.juso.main.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.juso.main.entity.MyHistKeyword;

public interface MyKeywordRepository extends CrudRepository<MyHistKeyword, String> {
	@SuppressWarnings("unchecked")
	MyHistKeyword save(MyHistKeyword myKeyword);
	List<MyHistKeyword> findTop10ByIdOrderByRegTimeDesc(String id);
}
