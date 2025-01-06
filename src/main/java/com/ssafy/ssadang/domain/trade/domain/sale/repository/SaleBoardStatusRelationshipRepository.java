package com.ssafy.ssadang.domain.trade.domain.sale.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleBoardStatusRelationship;

public interface SaleBoardStatusRelationshipRepository extends JpaRepository<SaleBoardStatusRelationship, Integer> {
	
	Set<SaleBoardStatusRelationship> findAllBySaleBoardId(Integer saleBoardId);

}
