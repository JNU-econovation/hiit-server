package com.hiit.api.repository.repository.bussiness.custom;

import com.hiit.api.repository.entity.business.BarEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class BarCustomRepositoryImpl extends QuerydslRepositorySupport
		implements BarCustomRepository {

	public BarCustomRepositoryImpl() {
		super(BarEntity.class);
	}
}
