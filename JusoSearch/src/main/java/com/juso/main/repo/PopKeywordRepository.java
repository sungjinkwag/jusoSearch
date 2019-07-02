package com.juso.main.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.juso.main.entity.PopKeyword;

public interface PopKeywordRepository extends CrudRepository<PopKeyword, String> {
	boolean existsById(String keyword);
	Optional<PopKeyword> findById(String keyword);
	@SuppressWarnings("unchecked")
	PopKeyword save(PopKeyword popKeyword);
	List<PopKeyword> findTop10ByOrderByCntDesc();
}
