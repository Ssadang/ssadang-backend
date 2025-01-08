package com.ssafy.ssadang.domain.trade.domain.share.entity;

import java.time.LocalDateTime;

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
import lombok.Setter;

@Entity
@Table(name = "share_board")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ShareBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shareBoardId;
	@NotNull
	private Integer authorId;
	@NotNull
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	@NotNull
	private String title;
	@NotNull
	private String description;
	private Integer maxParticipantCount;
	@NotNull
	private Integer itemCategoryId;
	@NotNull
	private Integer areaId;
	private Integer winnerId;
	private Integer gifticonId;
	@NotNull
	private Integer hitCount;
	@NotNull
	private Boolean gameSelected;
	
	public void increaseHitCount() {
		hitCount++;
	}
	
	public void updateWinnerid(Integer winnerId) {
		this.winnerId = winnerId;
	}

}
