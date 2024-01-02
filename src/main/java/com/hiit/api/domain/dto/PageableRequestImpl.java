package com.hiit.api.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class PageableRequestImpl implements PageRequest {

	private final Pageable pageable;

	@Override
	public int getPageNumber() {
		return pageable.getPageNumber();
	}

	@Override
	public int getPageSize() {
		return pageable.getPageSize();
	}

	@Override
	public long getOffset() {
		return pageable.getOffset();
	}

	@Override
	public Sort getSort() {
		return pageable.getSort();
	}

	@Override
	public Pageable next() {
		return pageable.next();
	}

	@Override
	public Pageable previousOrFirst() {
		return pageable.previousOrFirst();
	}

	@Override
	public Pageable first() {
		return pageable.first();
	}

	@Override
	public Pageable withPage(int pageNumber) {
		return pageable.withPage(pageNumber);
	}

	@Override
	public boolean hasPrevious() {
		return pageable.hasPrevious();
	}
}
