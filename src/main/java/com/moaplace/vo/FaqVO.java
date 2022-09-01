package com.moaplace.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FaqVO {

	private int faq_num;
	private int member_num;
	private int sort_num;
	private String sort_name;
	private String faq_title;
	private String faq_content;
		
}
