package com.moaplace.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PageUtil {
	private int pageNum;
	private int startRow;
	private int endRow;
	private int totalPageCount;
	private int startPageNum;
	private int endPageNum;
	private int rowBlockCount;
	private int pageBlockCount;
	private int totalRowCount;
	/**
	 * @param pageNum 페이지번호
	 * @param rowBlockCount 보여질 행의 갯수
	 * @param pageBlockCount 보여질 페이지 갯수
	 * @param totalRowCount 전체 행의 갯수
	 */
	public PageUtil(int pageNum, int rowBlockCount, int pageBlockCount, int totalRowCount) 
	{
		this.pageNum = pageNum;
		this.rowBlockCount = rowBlockCount;
		this.pageBlockCount = pageBlockCount;
		this.totalRowCount = totalRowCount;
		startRow = (pageNum -1) * rowBlockCount +1;
		endRow = startRow + rowBlockCount -1;
		totalPageCount = (int)Math.ceil(totalRowCount /(double)rowBlockCount);
		startPageNum = (pageNum -1)/pageBlockCount * pageBlockCount +1;
		endPageNum = startPageNum + pageBlockCount -1;
		if(totalPageCount < endPageNum) {
			endPageNum = totalPageCount;
		}
	}
	
	
	
}
