package com.hiit.api.domain.dao.it.in;

import com.hiit.api.domain.dao.JpaDao;
import java.util.List;

public interface InItDao extends JpaDao<InItData, Long> {

	List<InItData> findAllActiveStatusByMember(Long memberId);

	List<InItData> findAllEndStatusByMember(Long memberId);

	InItData findActiveStatusByIdAndMember(Long inItId, Long memberId);

	InItData findEndStatusByIdAndMember(Long inItId, Long memberId);

	InItData findActiveStatusByTargetIdAndMember(Long targetId, Long memberId);

	InItData findEndStatusByTargetIdAndMember(Long targetId, Long memberId);
}
