package com.hiit.api.domain.support.entity;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class PageElements<T> {

	private final int pageSize;
	private final int pageNumber;
	private final int totalPageCount;
	private final Long totalCount;
	private final List<T> data;

	public PageElements(Pageable pageable, int totalPageCount, Long totalCount, List<T> data) {
		this.pageSize = pageable.getPageSize();
		this.pageNumber = pageable.getPageNumber();
		this.totalPageCount = totalPageCount;
		this.totalCount = totalCount;
		this.data = data;
	}

	public PageElements(PageElements<?> source, List<T> data) {
		this.pageSize = source.getPageSize();
		this.pageNumber = source.getPageNumber();
		this.totalPageCount = source.getTotalPageCount();
		this.totalCount = source.getTotalCount();
		this.data = data;
	}
}
