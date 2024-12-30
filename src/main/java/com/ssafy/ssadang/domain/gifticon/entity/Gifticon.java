package com.ssafy.ssadang.domain.gifticon.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gifticon")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Gifticon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gifticonId;
	@NotNull
	private Integer ownerId;
	@Column(length = 2048)
	@NotNull
	private String imagePath;
	@NotNull
	private LocalDate expiryDate;
	@Column(length = 45)
	@NotNull
	private String name;

}
