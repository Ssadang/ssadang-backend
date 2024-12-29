package com.ssafy.ssadang.domain.gifticon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "gifticon_status_relationship")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GifticonStatusRelationship {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gifticonStatusRelationshipId;
	@NotNull
	private Integer gifticonId;
	@NotNull
	private Integer gifticonStatusId;

}
