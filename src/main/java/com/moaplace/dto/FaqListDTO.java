package com.moaplace.dto;

import lombok.Data;

@Data
public class FaqListDTO {
	
	private int rnum;
	private int faq_num;
	private String sort_name;
	private String faq_title;
	private String faq_content;	

}
