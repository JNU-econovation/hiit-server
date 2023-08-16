package com.hiit.api.repository.dao.bussiness;

import com.hiit.api.repository.dao.bussiness.custom.BarCustomRepository;
import com.hiit.api.repository.dao.bussiness.jpa.BarJpaRepository;
import com.hiit.api.repository.entity.business.BarEntity;
import org.springframework.data.repository.RepositoryDefinition;

/** 연관관계를 가지고 있는 엔티티의 경우 신경쓸 것이 더 많다. */
@RepositoryDefinition(domainClass = BarEntity.class, idClass = Long.class)
public interface BarRepository extends BarJpaRepository, BarCustomRepository {}
