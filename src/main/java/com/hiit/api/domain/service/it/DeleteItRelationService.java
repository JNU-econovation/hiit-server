package com.hiit.api.domain.service.it;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteItRelationService {

	private final ItRelationDao itRelationDao;

	@Transactional
	public void execute(Long itRelationId) {
		itRelationDao.deleteById(itRelationId);
	}
}
