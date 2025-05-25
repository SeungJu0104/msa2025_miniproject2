package org.minisecond.project.util;

import java.util.Iterator;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PageResponseVO<T> implements Iterable<T>{
	
	private int currentPage;
	private  final int pageGroupSize;
	private  int totalPages;
	private final int startPage;
	private final int endPage;
	private Page<T> paging;
	
	public PageResponseVO(int pageGroupSize, Page<T> page) {
		this.paging = page;
		this.pageGroupSize = pageGroupSize;
		this.totalPages = getTotalPages();
		this.startPage = (getNumber() / pageGroupSize) * pageGroupSize;
		this.endPage = Math.min(startPage + pageGroupSize - 1, totalPages - 1);
	}
	
	public boolean isPrevious() {
		return paging.hasPrevious();
	}

	public boolean isNext() {
		return paging.hasNext();
	}
	
	public boolean isEmpty() {
		return paging.isEmpty();
	}
	
	public long getTotalElements() {
		return paging.getTotalElements();
	}
	
	public int getTotalPages() {
		return paging.getTotalPages();
	}
	
	public int getNumber() {
		return paging.getNumber();
	}
	
	public long getIndexNumber(int index) {
		return paging.getTotalElements() - (paging.getNumber() * paging.getSize()) - index;
	}
	
	public List<T> getContent(){
		return paging.getContent();
	}
	
	public int getPreviousPage() {
	    return Math.max(0, startPage - pageGroupSize);
	}

	public int getNextPage() {
		if(startPage + pageGroupSize > getTotalPages()) return getTotalPages();
		else return startPage + pageGroupSize;
	}

	@Override
	public Iterator<T> iterator() {
		return paging.iterator();
	}

}
