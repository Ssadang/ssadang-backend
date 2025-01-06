package com.ssafy.ssadang.domain.trade.domain.sale.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleBoard;

public interface SaleBoardRepository extends JpaRepository<SaleBoard, Integer>, JpaSpecificationExecutor<SaleBoard> {
	
	List<SaleBoard> findAllByAuthorIdOrderByCreateDateDesc(Integer authorId);
	
	List<SaleBoard> findAllByItemCategoryIdOrderByCreateDateDesc(Integer itemCategoryId);
	
	Page<SaleBoard> findAll(Pageable pageable);
	
	Page<SaleBoard> findAllByCreateDateLessThan(LocalDateTime createDate, Pageable pageable);

}
