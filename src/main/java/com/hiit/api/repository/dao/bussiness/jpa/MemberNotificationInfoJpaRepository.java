package com.hiit.api.repository.dao.bussiness.jpa;

import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.member.MemberNotificationInfoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MemberNotificationInfoJpaRepository
		extends JpaRepository<MemberNotificationInfoEntity, Long> {

	List<MemberNotificationInfoEntity> findAllByHiitMemberEntity(HiitMemberEntity hiitMemberEntity);
}
