package com.moaplace.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShowVO {
	private int show_num; 			//공연번호
	private int genre_num; 			//장르번호
	private int hall_num; 			//공연장 번호
	private String show_name; 		//공연명
	private Date show_start; 		//공연시작일
	private Date show_end; 			//공연종료일
	private String show_check; 		//공연여부
	private Date stop_start; 		//공연중단시작일
	private Date stop_end; 			//공연중단종료일
	private String show_age; 		//상연등급(연령)
	private int intermission; 		//인터미션
	private int running_time; 		//러닝타임
	private byte[] show_thumbnail; 	//섬네일

}
