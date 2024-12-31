package com.ssafy.ssadang.domain.gifticon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.gifticon.entity.Gifticon;

public interface GifticonRepository extends JpaRepository<Gifticon, Integer> {
	
	List<Gifticon> findAllByOwnerIdOrderByExpiryDate(Integer ownerId);
	
	Page<Gifticon> findAllByGifticonId(Integer gifticonId, Pageable pageable);

}
