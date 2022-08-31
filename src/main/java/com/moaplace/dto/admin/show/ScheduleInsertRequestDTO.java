package com.moaplace.dto.admin.show;

import lombok.Data;

@Data
public class ScheduleInsertRequestDTO {
	
	private int showNum;
	private String[] showTime;
	private String showDate;
}
