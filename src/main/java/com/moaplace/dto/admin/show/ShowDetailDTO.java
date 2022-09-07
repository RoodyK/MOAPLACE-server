package com.moaplace.dto.admin.show;

import java.util.List;

import com.moaplace.vo.GradeVO;

import lombok.Data;
	
@Data
public class ShowDetailDTO {
	private int num;					//공연번호
	private String title;				//공연명
	private int hall;					//공연장명
	private int genre;					//장르명
	private String status;				//공연상태
	private int runningTime;			//러닝타임
	private int intermission;			//인터미션
	private String age;					//상연등급
	private String startDate;			//공연시작일
	private String endDate;				//공연종료일
	private String blockStartDate;		//공연중단시작일
	private String blockEndDate;		//공연중단종료일
	private String showThumbnail;
	List<GradeVO> seatPrice;
	List<String> imgDetails;
}
