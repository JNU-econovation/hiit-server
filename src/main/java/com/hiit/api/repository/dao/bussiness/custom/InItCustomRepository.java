package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.List;
import java.util.Optional;

public interface InItCustomRepository {

	// 특정 참여 잇을 삭제한다.
	void deleteByInItId(Long id);

	// 특정 참여 잇의 상태를 종료로 변경 및 종료 제목을 설정하고, 특정 참여 잇과 연관된 잇 연관 엔티티를 모두 삭제한다.
	void endByIdWithItRelation(Long id, String title);

	// 특정 타겟 아이디와 특정 상태, 특정 요일 코드를 가진 모든 잇을 조회한다.
	List<InItEntity> findAllByItIdAndStatusAndDayCode(
			Long itId, ItStatus status, DayCodeList dayCode);

	/** 특정 멤버와 특정 타겟 아이디, 특정 상태를 가진 잇을 조회한다. */
	Optional<InItEntity> findByHiitMemberAndTargetIdAndStatus(
			HiitMemberEntity hiitMember, Long itId, ItStatus status);
}
