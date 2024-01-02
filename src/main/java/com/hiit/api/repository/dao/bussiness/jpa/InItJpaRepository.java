package com.hiit.api.repository.dao.bussiness.jpa;

import com.hiit.api.repository.dao.UniqueOrOptionalParam;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InItJpaRepository extends JpaRepository<InItEntity, Long> {

	/** 특정 멤버가 가진 특정 상태의 모든 참여 잇을 조회한다. */
	List<InItEntity> findAllByHiitMemberAndStatus(HiitMemberEntity member, ItStatus status);

	/** 특정 아이디 값과 특정 멤버를 가진 특정 상태의 잇을 조회한다. */
	Optional<InItEntity> findByIdAndHiitMemberAndStatus(
			@UniqueOrOptionalParam Long id, HiitMemberEntity member, ItStatus status);
}
