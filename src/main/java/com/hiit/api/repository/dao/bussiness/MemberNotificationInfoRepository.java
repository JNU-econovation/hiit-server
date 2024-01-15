package com.hiit.api.repository.dao.bussiness;

import com.hiit.api.repository.dao.bussiness.jpa.MemberNotificationInfoJpaRepository;
import com.hiit.api.repository.entity.business.member.MemberNotificationInfoEntity;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = MemberNotificationInfoEntity.class, idClass = Long.class)
public interface MemberNotificationInfoRepository extends MemberNotificationInfoJpaRepository {}
