package com.moaplace.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class AdminNoticeVO {
   private int notice_num;
   private int member_num;
   private int sort_num;
   private String notice_title;
   private String notice_content;
   @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
   private Date notice_regdate;
   private int notice_hit;
}
