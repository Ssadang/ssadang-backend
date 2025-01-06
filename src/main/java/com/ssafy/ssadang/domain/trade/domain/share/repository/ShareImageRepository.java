package com.ssafy.ssadang.domain.trade.domain.share.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareImage;

public interface ShareImageRepository extends JpaRepository<ShareImage, Integer> {
	
	List<ShareImage> findAllByShareBoardId(Integer shareBoardId);

}
