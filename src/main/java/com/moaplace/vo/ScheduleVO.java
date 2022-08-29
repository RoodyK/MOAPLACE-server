package com.moaplace.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleVO {
	private int schedule_num;
	private int show_num;
	private String schedule_time;
	private String schedule_date;
	private char schedule_YN;
}
