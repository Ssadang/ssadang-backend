package com.ssafy.ssadang.domain.trade.domain.sale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleImage;

public interface SaleImageRepository extends JpaRepository<SaleImage, Integer> {
	
	List<SaleImage> findAllBySaleBoardId(Integer saleBoardId);

}
