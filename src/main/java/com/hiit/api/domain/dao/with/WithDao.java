package com.hiit.api.domain.dao.with;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.domain.dao.support.PageData;
import com.hiit.api.domain.dao.support.PageableInfo;
import com.hiit.api.domain.dao.support.Period;
import java.util.List;
import java.util.Optional;

public interface WithDao extends JpaDao<WithData, Long> {

	Long countByInIt(Long inIt);

	boolean existsById(Long id);

	PageData<WithData> findAllByInItAndMember(Long init, PageableInfo pageable, Long member);

	PageData<WithData> findAllByInIt(Long init, PageableInfo pageable);

	List<WithData> findAllByInItAndMember(Long init, Long member);

	Optional<WithData> findByInItEntityAndMemberAndPeriod(Long inIt, Long member, Period period);
}
