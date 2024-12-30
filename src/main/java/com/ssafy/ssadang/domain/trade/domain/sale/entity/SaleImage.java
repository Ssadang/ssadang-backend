package com.ssafy.ssadang.domain.trade.domain.sale.entity;

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
@Table(name = "sale_image")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SaleImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer imageId;
	@NotNull
	@Column(length = 2048)
	private String path;
	@NotNull
	private Integer saleBoardId;

}
