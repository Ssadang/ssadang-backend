package com.ssafy.ssadang.domain.trade.domain.sale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleBoard;

public interface SaleBoardRepository extends JpaRepository<SaleBoard, Integer> {
	
	List<SaleBoard> findAllByAuthorIdOrderByCreateDateDesc(Integer authorId);
	
	List<SaleBoard> findAllByItemCategoryIdOrderByCreateDateDesc(Integer itemCategoryId);

}
