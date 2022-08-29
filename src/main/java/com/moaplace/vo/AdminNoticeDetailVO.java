package com.moaplace.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class AdminNoticeDetailVO {
  private int notice_detail_num;
  private int notice_num;
  private String notice_orgfile;
  private String notice_savefile;
  private long notice_filesize;
}
