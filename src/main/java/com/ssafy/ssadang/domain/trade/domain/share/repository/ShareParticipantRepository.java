package com.ssafy.ssadang.domain.trade.domain.share.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.trade.domain.share.entity.ShareParticipant;

public interface ShareParticipantRepository extends JpaRepository<ShareParticipant, Integer> {
	
	Optional<ShareParticipant> findByShareBoardIdAndUserId(Integer shareBoardId, Integer userId);

	Set<ShareParticipant> findAllByShareBoardId(Integer shareBoardId);
	
	void deleteByShareBoardIdAndUserId(Integer shareBoardId, Integer userId);

}
