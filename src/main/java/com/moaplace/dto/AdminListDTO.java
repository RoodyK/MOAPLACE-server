package com.moaplace.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AdminListDTO {
   private int notice_num;
   private String notice_title;
   @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
   private Date notice_regdate;
   private int sort_num;
   private String sort_name;
}
