package com.hiit.api.domain.dao.with;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.support.entity.PageElements;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.util.List;
import java.util.Optional;

public interface WithDao extends JpaDao<WithEntity, Long> {

	Long countByInIt(Long inItId);

	boolean existsById(Long withId);

	PageElements<WithEntity> findAllByInItAndMember(
			Long initId, PageRequest pageable, Long memberId, Period period);

	PageElements<WithEntity> findAllByInIt(Long initId, PageRequest pageable, Period period);

	List<WithEntity> findAllByInItAndMember(Long initId, Long memberId);

	Optional<WithEntity> findByInItEntityAndMemberAndPeriod(
			Long inItId, Long memberId, Period period);
}
