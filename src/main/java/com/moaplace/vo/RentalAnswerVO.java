package com.moaplace.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalAnswerVO {
	
	private int answer_num; //답변번호
	private int member_num; //글쓴 회원번호
	private int rental_num; // 신청서번호
	private String answer_content; //답변
	private Date regdate; //등록일
	
}
