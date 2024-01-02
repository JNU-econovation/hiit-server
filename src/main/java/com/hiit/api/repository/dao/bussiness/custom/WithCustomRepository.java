package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WithCustomRepository {

	/** 특정 잇과 페이징 정보 그리고 특정 멤버 필터링 여부를 통해 페이징 처리된 with 목록을 반환한다. */
	Page<WithEntity> findAllByInIt(
			InItEntity init,
			Pageable pageable,
			@Nullable HiitMemberEntity member,
			LocalDateTime startTime,
			LocalDateTime endTime);

	/** 특정 잇과 특정 멤버를 가진 with 목록을 반환한다. */
	List<WithEntity> findAllByInIt(InItEntity init, HiitMemberEntity member);

	/** 특정 시간 사이에 특정 잇과 특정 멤버를 가진 with을 반환한다. */
	List<WithEntity> findAllByInItEntityAndHiitMemberAndCreateAtBetween(
			InItEntity inIt,
			HiitMemberEntity hiitMemberEntity,
			LocalDateTime startTime,
			LocalDateTime endTime);
}
