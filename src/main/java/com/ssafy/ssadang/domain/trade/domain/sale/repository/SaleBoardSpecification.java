package com.ssafy.ssadang.domain.trade.domain.sale.repository;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;

import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleBoard;

import jakarta.persistence.criteria.Predicate;

public class SaleBoardSpecification {
	
	public static Specification<SaleBoard> hasAllKeywordsIn(String[] keywords) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = Arrays.stream(keywords)
                .map(keyword -> criteriaBuilder.like(root.get("title"), "%" + keyword + "%"))
                .toArray(Predicate[]::new);
            return criteriaBuilder.and(predicates);
        };
	}
	
	public static Specification<SaleBoard> isCreateDateLessThanAndHasAllKeywordsIn(LocalDateTime createDate, String[] keywords) {
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
