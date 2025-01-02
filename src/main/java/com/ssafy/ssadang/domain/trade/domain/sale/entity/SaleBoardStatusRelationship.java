package com.ssafy.ssadang.domain.trade.domain.sale.entity;

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
@Table(name = "sale_board_status_relationship")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SaleBoardStatusRelationship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer saleBoardRelationshipId;
	@NotNull
	private Integer saleBoardId;
	@NotNull
	private Integer boardStatusId;

}
