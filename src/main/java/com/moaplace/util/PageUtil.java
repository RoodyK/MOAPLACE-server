package com.moaplace.util; 

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class PageUtil {
	private int pageNum; // 페이지번호
	private int startRow; // 시작행 번호
	private int endRow; // 끝번호
	private int totalPageCount; // 전체  페이지 개수
	private int startPageNum; // 시작 페이지 번호
	private int endPageNum; // 끝페이지 번호
	private int rowBlockCount; // 한페이지에 보여질 글의 개수
	private int pageBlockCount; // 한 페이지에 보여질 페이지 개수
	private int totalRowCount; // 전체 글의 개수
	/**
	 * @param pageNum        페이지 번호
	 * @param rowBlockCount  보여질 행의 갯수 
	 * @param pageBlockCount 보여질 페이지 갯수
	 * @param totalRowCount  전체 행의 갯수
	 */
	public PageUtil(int pageNum, int rowBlockCount, int pageBlockCount, int totalRowCount) {
		this.pageNum = pageNum;
		this.rowBlockCount = rowBlockCount;
		this.pageBlockCount = pageBlockCount;
		this.totalRowCount = totalRowCount;
		startRow = (pageNum - 1) * rowBlockCount + 1;
		endRow = startRow + rowBlockCount - 1;
		totalPageCount = (int) Math.ceil(totalRowCount / (double) rowBlockCount);
		startPageNum = (pageNum - 1) / pageBlockCount * pageBlockCount + 1;
		endPageNum = startPageNum + pageBlockCount - 1;
		if (totalPageCount < endPageNum) {
			endPageNum = totalPageCount;
		}
	}
}
