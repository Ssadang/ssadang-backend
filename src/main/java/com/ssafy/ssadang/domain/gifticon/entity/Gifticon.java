package com.ssafy.ssadang.domain.gifticon.entity;

import java.time.LocalDate;

import com.ssafy.ssadang.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "gifticon")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Gifticon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gifticonId;
	@JoinColumn(name = "owner_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User owner;
	private String imagePath;
	private LocalDate expiryDate;
	private String name;
	private Boolean used;

}
