package com.moaplace.dto.admin.show;

import java.util.List;

import lombok.Data;

@Data
public class ShowDetailViewDTO {
	
	private int num;					//공연번호
	private String title;				//공연명
	private String hall;				//공연장명
	private String genre;				//장르명
	private String status;				//공연상태
	private int runningTime;			//러닝타임
	private int intermission;			//인터미션
	private int seats;					//총좌석수
	private String age;					//상연등급
	private String startDate;			//공연시작일
	private String endDate;				//공연종료일
	private String blockStartDate;		//공연중단시작일
	private String blockEndDate;		//공연중단종료일
	private String thumbnail;			//섬네일
	private List<String> detailImgs;	//상세이미지
}
