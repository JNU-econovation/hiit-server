package com.hiit.api.repository.repository.bussiness.custom;

import com.hiit.api.repository.entity.business.FooEntity;
import com.hiit.api.repository.entity.business.QFooEntity;
import com.hiit.api.repository.entity.business.param.FooEntitySearchParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class FooCustomRepositoryImpl extends QuerydslRepositorySupport
		implements FooCustomRepository {

	public FooCustomRepositoryImpl() {
		super(FooEntity.class);
	}

	@Override
	public Page<FooEntity> findAllByNameAndDeletedFalse(Pageable pageable, String name) {

		QFooEntity foo = QFooEntity.fooEntity;

		JPQLQuery<FooEntity> query =
				from(foo)
						.where(foo.name.eq(name), foo.deleted.isFalse())
						.orderBy(foo.id.desc())
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize());

		return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
	}

	@Override
	public Page<FooEntity> search(Pageable pageable, FooEntitySearchParam param) {
		QFooEntity foo = QFooEntity.fooEntity;

		JPQLQuery<FooEntity> query =
				from(foo).where(getGtId(param.getId()), getEqName(param.getName()));

		return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
	}

	private BooleanExpression getGtId(Optional<Long> param) {
		BooleanExpression gtId = null;
		if (param.isPresent()) {
			gtId = QFooEntity.fooEntity.id.gt(param.get());
		}
		return gtId;
	}

	private BooleanExpression getEqName(Optional<String> param) {
		BooleanExpression eqName = null;
		if (param.isPresent()) {
			eqName = QFooEntity.fooEntity.name.eq(param.get());
		}
		return eqName;
	}
}