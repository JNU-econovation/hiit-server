package com.hiit.api.repository.dao.bussiness;

import com.hiit.api.repository.dao.bussiness.custom.HitCustomRepository;
import com.hiit.api.repository.dao.bussiness.jpa.HitJpaRepository;
import com.hiit.api.repository.entity.business.hit.HitEntity;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = HitEntity.class, idClass = Long.class)
public interface HitRepository extends HitJpaRepository, HitCustomRepository {}
