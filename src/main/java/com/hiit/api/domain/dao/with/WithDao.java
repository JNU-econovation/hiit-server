package com.hiit.api.domain.dao.with;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.domain.dao.support.PageData;
import com.hiit.api.domain.dao.support.PageableInfo;
import com.hiit.api.domain.dao.support.Period;

public interface WithDao extends JpaDao<WithData, Long> {

	Long countByInIt(Long inIt);

	boolean existsById(Long id);

	PageData<WithData> findAllByInIt(Long init, PageableInfo pageable, Long member);

	WithData findByInItEntityAndMemberAndPeriod(Long inIt, Long member, Period period);
}
