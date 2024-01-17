package com.hiit.api.domain.service.it;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.repository.entity.business.it.ItStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItActiveMemberCountService {

	private final ItRelationDao itRelationDao;

	@Transactional(readOnly = true)
	public Long execute(GetItId itId) {
		return itRelationDao.countByItIdAndStatus(itId.getId(), ItStatus.ACTIVE);
	}
}
