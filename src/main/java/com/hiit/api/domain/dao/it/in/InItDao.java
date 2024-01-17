package com.hiit.api.domain.dao.it.in;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.repository.entity.business.it.InItEntity;
import java.util.List;
import java.util.Optional;

public interface InItDao extends JpaDao<InItEntity, Long> {

	List<InItEntity> findAllActiveStatusByMemberId(Long memberId);

	List<InItEntity> findAllEndStatusByMember(Long memberId);

	Optional<InItEntity> findActiveStatusByIdAndMember(Long inItId, Long memberId);

	Optional<InItEntity> findEndStatusByIdAndMember(Long inItId, Long memberId);

	Optional<InItEntity> findActiveStatusByItIdAndMember(Long itItd, Long memberId);

	Optional<InItEntity> findEndStatusByItIdAndMember(Long itId, Long memberId);

	void endByIdWithItRelation(Long inItId, String title);

	void deleteByIdWithItRelation(Long inItId);
}
