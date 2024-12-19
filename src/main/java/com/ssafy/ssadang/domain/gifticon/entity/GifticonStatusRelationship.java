package com.ssafy.ssadang.domain.gifticon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity(name = "gifticon_status_relationship")
@AllArgsConstructor
@Builder
@Getter
public class GifticonStatusRelationship {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gifticonStatusRelationshipId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gifticon_id")
	@NotNull
	private Gifticon gifticon;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gifticon_status_id")
	@NotNull
	private GifticonStatus gifticonStatus;

}
