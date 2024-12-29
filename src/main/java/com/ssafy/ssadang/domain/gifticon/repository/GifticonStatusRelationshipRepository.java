package com.ssafy.ssadang.domain.gifticon.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.gifticon.entity.GifticonStatusRelationship;

public interface GifticonStatusRelationshipRepository extends JpaRepository<GifticonStatusRelationship, Integer> {
	
	Set<GifticonStatusRelationship> findAllByGifticonId(Integer gifticonId);

}
