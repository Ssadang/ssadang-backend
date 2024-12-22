package com.ssafy.ssadang.domain.gifticon.entity;

import java.time.LocalDate;
import java.util.Set;

import com.ssafy.ssadang.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
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
	@NotNull
	private User owner;
	@Column(length = 2048)
	@NotNull
	private String imagePath;
	@NotNull
	private LocalDate expiryDate;
	@Column(length = 45)
	@NotNull
	private String name;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gifticon")
	private Set<GifticonStatusRelationship> gifticonStatusRelationships;

}
