package com.ssafy.ssadang.domain.trade.service;

import org.springframework.stereotype.Service;

import com.ssafy.ssadang.domain.trade.entity.ItemCategory;
import com.ssafy.ssadang.domain.trade.repository.ItemCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {
	
	private final ItemCategoryRepository itemCategoryRepository;

	@Override
	public ItemCategory findById(Integer id) {
		return itemCategoryRepository.findById(id).orElseThrow();
	}

}
