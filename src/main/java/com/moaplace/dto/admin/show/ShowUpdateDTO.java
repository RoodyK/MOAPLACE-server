package com.moaplace.dto.admin.show;

import java.sql.Date;

import lombok.Data;
@Data
public class ShowUpdateDTO {

	private int showNum;				//공연번호
	private String hall;				//공연장명
	private String genre;				//장르명
	private String title;				//공연명
	private int rprice;					//R석 가격
	private int sprice;					//S석 가격
	private int aprice;					//A석 가격
	private String going;				//공연상태
	private int runningTime;			//러닝타임
	private int intermission;			//인터미션
	private String showAge;				//상연등급
	private Date startDate;			 	//공연시작일
	private Date endDate;				//공연종료일
	private Date pauseStart;			//공연중단시작일
	private Date pauseEnd;				//공연중단종료일
	private String showThumbnail;		//공연썸네일
	private String[] imgDetails;		//공연상세이미지

}
