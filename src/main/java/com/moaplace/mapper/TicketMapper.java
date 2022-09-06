package com.moaplace.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.moaplace.vo.TicketVO;

@Mapper
public interface TicketMapper {
	int ticketInsert(TicketVO vo);
}
