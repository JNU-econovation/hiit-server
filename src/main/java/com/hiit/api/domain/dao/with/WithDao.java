package com.hiit.api.domain.dao.with;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.support.entity.PageElements;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.hiit.api.repository.entity.business.with.WithStatus;
import java.util.List;
import java.util.Optional;

public interface WithDao extends JpaDao<WithEntity, Long> {

	Long countByInIt(Long inItId);

	Long countEndByInIt(Long inItId);

	boolean existsById(Long withId);

	boolean existsByIdAndStatus(Long withId, WithStatus status);

	PageElements<WithEntity> findAllByInItAndMemberAndStatus(
			Long initId, PageRequest pageable, Long memberId, Period period, WithStatus status);

	PageElements<WithEntity> findAllByInItAndStatus(
			Long initId, PageRequest pageable, Period period, WithStatus status);

	PageElements<WithEntity> findAllByInItRandomAndStatus(
			Long initId, Integer size, WithStatus status);

	List<WithEntity> findAllByInItAndMemberAndStatus(Long initId, Long memberId, WithStatus status);

	List<WithEntity> findAllByInItAndMemberAndStatusEnd(Long initId, Long memberId);

	Optional<WithEntity> findByInItEntityAndMemberAndPeriod(
			Long inItId, Long memberId, Period period);
}
