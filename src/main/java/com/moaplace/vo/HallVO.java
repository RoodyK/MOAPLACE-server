package com.moaplace.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HallVO {
	private int hall_num;
	private String hall_name;
	private int hall_price;
	private int hall_cols;
	private int hall_rows;
}
