package com.hiit.api.domain.dao.it.in;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.repository.entity.business.it.InItEntity;
import java.util.List;

public interface InItDao extends JpaDao<InItEntity, Long> {

	List<InItEntity> findAllActiveStatusByMember(Long memberId);

	List<InItEntity> findAllEndStatusByMember(Long memberId);

	InItEntity findActiveStatusByIdAndMember(Long inItId, Long memberId);

	InItEntity findEndStatusByIdAndMember(Long inItId, Long memberId);

	InItEntity findActiveStatusByTargetIdAndMember(Long targetId, Long memberId);

	InItEntity findEndStatusByTargetIdAndMember(Long targetId, Long memberId);
}
