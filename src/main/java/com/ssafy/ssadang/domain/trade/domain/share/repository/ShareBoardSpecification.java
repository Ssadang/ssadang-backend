package com.ssafy.ssadang.domain.trade.domain.share.repository;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;

import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareBoard;

import jakarta.persistence.criteria.Predicate;

public class ShareBoardSpecification {
	
	public static Specification<ShareBoard> hasAllKeywordsIn(String[] keywords) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = Arrays.stream(keywords)
                .map(keyword -> criteriaBuilder.like(root.get("title"), "%" + keyword + "%"))
                .toArray(Predicate[]::new);
            return criteriaBuilder.and(predicates);
        };
	}
	
	public static Specification<ShareBoard> isCreateDateLessThanAndHasAllKeywordsIn(LocalDateTime createDate, String[] keywords) {
		return (root, query, criteriaBuilder) -> {
			Predicate createDatePredicate = criteriaBuilder.lessThan(root.get("createDate"), createDate);
			Predicate keywordPredicate = criteriaBuilder.and(
					Arrays.stream(keywords)
			                .map(keyword -> criteriaBuilder.like(root.get("title"), "%" + keyword + "%"))
			                .toArray(Predicate[]::new));
            return criteriaBuilder.and(createDatePredicate, keywordPredicate);
        };
	}

}
