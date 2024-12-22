package com.ssafy.ssadang.domain.gifticon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssadang.domain.gifticon.entity.Gifticon;
import com.ssafy.ssadang.domain.user.entity.User;

public interface GifticonRepository extends JpaRepository<Gifticon, Integer> {
	
	List<Gifticon> findAllByOwnerOrderByExpiryDate(User owner);

}
