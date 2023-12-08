package com.hiit.api.web.support;

import com.hiit.api.domain.dao.support.PageData;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

/** 페이지 객체 */
@Getter
public class Page<T> {

	private final int pageSize;
	private final int pageNumber;
	private final int totalPageCount;
	private final Long totalCount;
	private final List<T> data;

	public Page(final PageData<T> source) {
		this.pageSize = source.getPageSize();
		this.pageNumber = source.getPageNumber();
		this.totalPageCount = source.getTotalPageCount();
		this.totalCount = source.getTotalCount();
		this.data = source.getData();
	}

	// todo : remove
	public Page(final org.springframework.data.domain.Page<T> source) {
		final Pageable pageable = source.getPageable();
		this.pageSize = pageable.getPageSize();
		this.pageNumber = pageable.getPageNumber();
		this.totalPageCount = source.getTotalPages();
		this.totalCount = source.getTotalElements();
		this.data = source.getContent();
	}
}
