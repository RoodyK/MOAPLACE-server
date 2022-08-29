package com.moaplace.dto.admin.show;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class ShowInsertRequestDTO {
	private int genre_num; 							//장르번호
	private int hall_num; 							//공연장 번호
	private String show_name; 						//공연명
	private Date show_start; 						//공연시작일
	private Date show_end; 							//공연종료일
	private String show_check; 						//공연여부
	private String show_age; 						//상연등급(연령)
	private int intermission; 						//인터미션
	private int running_time; 						//러닝타임
	private String show_thumbnail; 					//섬네일
	private String[] show_detail_img;				//상세이미지
	private int rprice;								//R석 가격
	private int sprice;								//S석 가격
	private int aprice;								//A석 가격
	private int pageNum;
	private String status;
	private String field;
	private String search;
}
