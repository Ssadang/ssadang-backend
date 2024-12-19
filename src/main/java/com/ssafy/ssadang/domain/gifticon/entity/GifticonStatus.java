package com.ssafy.ssadang.domain.gifticon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity(name = "gifticon_status")
@AllArgsConstructor
@Getter
public class GifticonStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gifticonStatusId;
	@Column(length = 45)
	@NotNull
	private String name;

}
