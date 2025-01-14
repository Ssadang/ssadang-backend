package com.ssafy.ssadang.domain.trade.repository;

import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;

import com.ssafy.ssadang.domain.trade.entity.ItemCategory;

import jakarta.persistence.criteria.Predicate;

public class ItemCategorySpecification {

	public static Specification<ItemCategory> hasAllKeywordsIn(String[] keywords) {
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.and(
					Arrays.stream(keywords)
					.map(keyword -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%"))
					.toArray(Predicate[]::new));
		};
	}

}
