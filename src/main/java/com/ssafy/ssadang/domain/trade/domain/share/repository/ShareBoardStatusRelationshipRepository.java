package com.ssafy.ssadang.domain.trade.domain.share.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareBoardStatusRelationship;

public interface ShareBoardStatusRelationshipRepository extends JpaRepository<ShareBoardStatusRelationship, Integer> {
	
	Set<ShareBoardStatusRelationship> findAllByShareBoardId(Integer shareBoardId);

}
