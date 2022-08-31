package com.moaplace.dto.admin.show;

import java.util.List;

import lombok.Data;

@Data
public class ScheduleUpdateRequestDTO {

	private int showNum;
	private String showDate;
	private List<AdminScheduleDetailTimeDTO> list;

}
