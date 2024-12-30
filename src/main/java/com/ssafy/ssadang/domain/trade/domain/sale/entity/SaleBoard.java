package com.ssafy.ssadang.domain.trade.domain.sale.entity;

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

@Entity
@Table(name = "sale_board")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SaleBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer saleBoardId;
	@NotNull
	private Integer authorId;
	@NotNull
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	@NotNull
	private String title;
	@NotNull
	private String description;
	@NotNull
	private Integer price;
	@NotNull
	private Integer itemCategoryId;
	@NotNull
	private Integer areaId;
	private Integer boardStatusId;
	private Integer gifticonId;
	@NotNull
	private Integer hitCount;

}
