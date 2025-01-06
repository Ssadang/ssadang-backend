package com.ssafy.ssadang.domain.trade.domain.share.entity;

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
@Table(name = "share_board_status")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ShareBoardStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer boardStatusId;
	@NotNull
	private String name;

}
