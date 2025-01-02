package com.ssafy.ssadang.domain.trade.domain.sale.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.trade.domain.sale.entity.SaleFavorite;

public interface SaleFavoriteRepository extends JpaRepository<SaleFavorite, Integer> {
	
	Set<SaleFavorite> findAllBySaleBoardId(Integer saleBoardId);
	
	void deleteBySaleBoardIdAndUserId(Integer saleBoardId, Integer userId);

}
