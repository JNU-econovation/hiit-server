package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.BarEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BarCustomRepositoryImpl extends QuerydslRepositorySupport
		implements BarCustomRepository {

	public BarCustomRepositoryImpl() {
		super(BarEntity.class);
	}
}
