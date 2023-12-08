package com.hiit.api.domain.dao.support;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class PageData<T> {

	private final int pageSize;
	private final int pageNumber;
	private final int totalPageCount;
	private final Long totalCount;
	private final List<T> data;

	public PageData(Pageable pageable, int totalPageCount, Long totalCount, List<T> data) {
		this.pageSize = pageable.getPageSize();
		this.pageNumber = pageable.getPageNumber();
		this.totalPageCount = totalPageCount;
		this.totalCount = totalCount;
		this.data = data;
	}
}
