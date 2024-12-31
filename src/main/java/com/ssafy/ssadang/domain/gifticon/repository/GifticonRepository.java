package com.ssafy.ssadang.domain.gifticon.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.ssadang.domain.gifticon.entity.Gifticon;

public interface GifticonRepository extends JpaRepository<Gifticon, Integer> {
	
	Page<Gifticon> findAllByExpiryDateLessThanOrderByExpiryDateAscGifticonIdAsc(LocalDate expiryDate, Pageable pageable);
	
	Page<Gifticon> findAllByExpiryDateGreaterThanEqualOrderByExpiryDateAscGifticonIdAsc(LocalDate expiryDate, Pageable pageable);
	
	@Query("SELECT g FROM Gifticon g "
			+ "WHERE g.expiryDate < :now AND ( "
			+ "g.expiryDate > :expiryDate OR (g.expiryDate = :expiryDate AND g.gifticonId > :gifticonId))"
			+ "ORDER BY g.expiryDate, g.gifticonId")
	Page<Gifticon> findExpiredGifticon(@Param("now") LocalDate now,
			@Param("expiryDate") LocalDate expiryDate, @Param("gifticonId") Integer gifticonId, Pageable pageable);
	
	@Query("SELECT g FROM Gifticon g "
			+ "WHERE g.expiryDate >= :now AND ( "
			+ "g.expiryDate > :expiryDate OR (g.expiryDate = :expiryDate AND g.gifticonId > :gifticonId))"
			+ "ORDER BY g.expiryDate, g.gifticonId")
	Page<Gifticon> findUnexpiredGifticon(@Param("now") LocalDate now,
			@Param("expiryDate") LocalDate expiryDate, @Param("gifticonId") Integer gifticonId, Pageable pageable);

}
