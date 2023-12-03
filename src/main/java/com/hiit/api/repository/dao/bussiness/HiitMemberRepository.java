package com.hiit.api.repository.dao.bussiness;

import com.hiit.api.repository.dao.bussiness.custom.HiitMemberCustomRepository;
import com.hiit.api.repository.dao.bussiness.jpa.HiitMemberJpaRepository;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = HiitMemberEntity.class, idClass = Long.class)
public interface HiitMemberRepository extends HiitMemberJpaRepository, HiitMemberCustomRepository {}
