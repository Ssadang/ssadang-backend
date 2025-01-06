package com.ssafy.ssadang.domain.trade.domain.share.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareFavorite;

public interface ShareFavoriteRepository extends JpaRepository<ShareFavorite, Integer> {
	
	Set<ShareFavorite> findAllByShareBoardId(Integer shareBoardId);
	
	void deleteByShareBoardIdAndUserId(Integer shareBoardId, Integer userId);

}
