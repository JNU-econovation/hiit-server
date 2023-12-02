package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.FooEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FooCustomRepository {

	/** 페이지네이션을 사용하는 경우 직접 쿼리를 작성하자 */
	Page<FooEntity> findAllByNameAndDeletedFalse(Pageable pageable, String name);

	/** 검색과 같이 동적으로 쿼리가 바껴야하는 경우 쿼리를 작성하자. */
	Page<FooEntity> search(Pageable pageable, FooEntitySearchParam param);
}
