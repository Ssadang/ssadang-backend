package com.ssafy.ssadang.domain.trade.domain.share.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareBoard;

public interface ShareBoardRepository extends JpaRepository<ShareBoard, Integer>, JpaSpecificationExecutor<ShareBoard> {
	
	List<ShareBoard> findAllByAuthorIdOrderByCreateDateDesc(Integer authorId);
	
	List<ShareBoard> findAllByItemCategoryIdOrderByCreateDateDesc(Integer itemCategoryId);
	
	Page<ShareBoard> findAll(Pageable pageable);
	
	Page<ShareBoard> findAllByCreateDateLessThan(LocalDateTime createDate, Pageable pageable);

}
