package com.ssafy.ssadang.domain.trade.domain.share.repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareBoard;
import com.ssafy.ssadang.domain.trade.entity.ItemCategory;

import jakarta.persistence.criteria.Predicate;

public class ShareBoardSpecification {
	
	public static Specification<ShareBoard> hasAllKeywordsIn(String[] keywords, List<ItemCategory> itemCategories) {
        return (root, query, criteriaBuilder) -> {
			Predicate titlePredicate = criteriaBuilder.and(
					Arrays.stream(keywords)
							.map(keyword -> criteriaBuilder.like(root.get("title"), "%" + keyword + "%"))
							.toArray(Predicate[]::new));
			Predicate contentPredicate = criteriaBuilder.and(
					Arrays.stream(keywords)
							.map(keyword -> criteriaBuilder.like(root.get("content"), "%" + keyword + "%"))
							.toArray(Predicate[]::new));
			Predicate itemCategoryPredicate = criteriaBuilder.or(
					itemCategories.stream()
							.map(itemCategory -> criteriaBuilder.equal(root.get("itemCategoryId"), itemCategory.getItemCategoryId()))
							.toArray(Predicate[]::new));
			return criteriaBuilder.or(titlePredicate, contentPredicate, itemCategoryPredicate);
        };
	}
	
	public static Specification<ShareBoard> isCreateDateLessThanAndHasAllKeywordsIn(
			LocalDateTime createDate, String[] keywords, List<ItemCategory> itemCategories) {
		return (root, query, criteriaBuilder) -> {
			Predicate createDatePredicate = criteriaBuilder.lessThan(root.get("createDate"), createDate);
			Predicate titlePredicate = criteriaBuilder.and(
					Arrays.stream(keywords)
							.map(keyword -> criteriaBuilder.like(root.get("title"), "%" + keyword + "%"))
							.toArray(Predicate[]::new));
			Predicate contentPredicate = criteriaBuilder.and(
					Arrays.stream(keywords)
							.map(keyword -> criteriaBuilder.like(root.get("content"), "%" + keyword + "%"))
							.toArray(Predicate[]::new));
			Predicate itemCategoryPredicate = criteriaBuilder.or(
					itemCategories.stream()
							.map(itemCategory -> criteriaBuilder.equal(root.get("itemCategoryId"), itemCategory.getItemCategoryId()))
							.toArray(Predicate[]::new));
			Predicate keywordPredicate = criteriaBuilder.or(titlePredicate, contentPredicate, itemCategoryPredicate);
			return criteriaBuilder.and(createDatePredicate, keywordPredicate);
		};
	}

}
