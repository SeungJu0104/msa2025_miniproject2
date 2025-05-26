package org.minisecond.project.util;

import java.util.List;

import lombok.Data;

@Data
public class PageResponseVO<T> {
	
	private String board;
	private List<T> list;  
	private int totalCount, totalPage, startPage, endPage, pageNo, size, parserPage;
	
	public PageResponseVO(String board, int pageNo, List<T> list, int totalCount, int size, int parserPage) {
		init(board, pageNo, list, totalCount, size, parserPage);
		calc(pageNo, list, totalCount, size, parserPage);
	}
	
	public PageResponseVO(int pageNo, List<T> list, int totalCount, int size, int parserPage) {
		init(pageNo, list, totalCount, size, parserPage);
		calc(pageNo, list, totalCount, size, parserPage);
	}

	public void init(String board, int pageNo, List<T> list, int totalCount, int size, int parserPage) {
		this.board = board;
		this.totalCount = totalCount;
		this.pageNo = pageNo;
		this.list = list;
		this.size = size;
		this.parserPage = parserPage;
	}
	
	public void init(int pageNo, List<T> list, int totalCount, int size, int parserPage) {
		this.totalCount = totalCount;
		this.pageNo = pageNo;
		this.list = list;
		this.size = size;
		this.parserPage = parserPage;
	}
	
	public void calc(int pageNo, List<T> list, int totalCount, int size, int parserPage) {
		totalPage =  (int)Math.ceil((double)totalCount / size);	   
		startPage = ((pageNo - 1) / parserPage) * parserPage + 1;
		endPage = ((pageNo - 1) / parserPage) * parserPage + parserPage;
		if (endPage > totalPage) endPage = totalPage;
	}

	public boolean isPrev() {
		return startPage != 1; 
	}
	
	public boolean isNext() {
		return totalPage != endPage;		
	}

}
