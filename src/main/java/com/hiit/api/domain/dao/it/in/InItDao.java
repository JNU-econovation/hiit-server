package com.hiit.api.domain.dao.it.in;

import com.hiit.api.repository.entity.business.it.InItEntity;
import java.util.List;
import java.util.Optional;

public interface InItDao {

	List<InItEntity> findAllActiveStatusByMemberId(Long memberId);

	List<InItEntity> findAllEndStatusByMemberId(Long memberId);

	Optional<InItEntity> findActiveStatusByIdAndMemberId(Long inItId, Long memberId);

	Optional<InItEntity> findEndStatusByIdAndMemberId(Long inItId, Long memberId);

	Optional<InItEntity> findActiveStatusByItIdAndMemberId(Long itItd, Long memberId);

	Optional<InItEntity> findEndStatusByItIdAndMemberId(Long itId, Long memberId);

	void endById(Long inItId, String title);

	void deleteById(Long inItId);

	InItEntity save(InItEntity source);

	Optional<InItEntity> findById(Long inItId);
}
