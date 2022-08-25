package com.moaplace.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AdminNoticeDetailDTO {
	  private int notice_detail_num;
	  private int notice_num;
	  private String notice_orgfile;
	  private String notice_savefile;
	  private long notice_filesize;
}
