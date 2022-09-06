package com.moaplace.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
//파일이 없는 경우, list 
public class AdminListDTO {
   private int notice_num;
   private String notice_title;
   private String notice_content;
   @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
   private Date notice_regdate;
   private int notice_hit;
   private int sort_num;
   private String sort_name;
   private int member_num;
}
