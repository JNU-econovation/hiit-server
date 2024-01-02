package com.hiit.api.domain.dao.it.in;

import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.repository.dao.bussiness.InItRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InitDaoImpl extends AbstractJpaDao<InItEntity, Long> implements InItDao {

	private final InItRepository repository;

	private final JsonConverter jsonConverter;

	public InitDaoImpl(
			JpaRepository<InItEntity, Long> jpaRepository,
			InItRepository repository,
			JsonConverter jsonConverter) {
		super(jpaRepository);
		this.repository = repository;
		this.jsonConverter = jsonConverter;
	}

	@Override
	public List<InItEntity> findAllActiveStatusByMember(Long memberId) {
		return findStatusByMember(memberId, ItStatus.ACTIVE);
	}

	@Override
	public List<InItEntity> findAllEndStatusByMember(Long memberId) {
		return findStatusByMember(memberId, ItStatus.END);
	}

	private List<InItEntity> findStatusByMember(Long memberId, ItStatus status) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		return repository.findAllByHiitMemberAndStatus(member, status);
	}

	@Override
	public InItEntity findActiveStatusByIdAndMember(Long inItId, Long memberId) {
		return findStatusByIdAndMember(memberId, inItId, ItStatus.ACTIVE);
	}

	@Override
	public InItEntity findEndStatusByIdAndMember(Long inItId, Long memberId) {
		return findStatusByIdAndMember(memberId, inItId, ItStatus.END);
	}

	private InItEntity findStatusByIdAndMember(Long memberId, Long inItId, ItStatus active) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		Optional<InItEntity> source = repository.findByIdAndHiitMemberAndStatus(inItId, member, active);
		if (source.isEmpty()) {
			String exceptionData = getExceptionData_M_IN(memberId, inItId);
			throw new DataNotFoundException(exceptionData);
		}
		return source.get();
	}

	private String getExceptionData_M_IN(Long memberId, Long inItId) {
		Map<String, Long> dataSource = new HashMap<>();
		dataSource.put("memberId", memberId);
		dataSource.put("inItId", inItId);
		return jsonConverter.toJson(dataSource);
	}

	@Override
	public InItEntity findActiveStatusByTargetIdAndMember(Long targetId, Long memberId) {
		return findStatusByTargetIdAndMember(targetId, memberId, ItStatus.ACTIVE);
	}

	@Override
	public InItEntity findEndStatusByTargetIdAndMember(Long targetId, Long memberId) {
		return findStatusByTargetIdAndMember(targetId, memberId, ItStatus.END);
	}

	private InItEntity findStatusByTargetIdAndMember(Long targetId, Long memberId, ItStatus status) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		Optional<InItEntity> source =
				repository.findByHiitMemberAndTargetIdAndStatus(member, targetId, status);
		if (source.isEmpty()) {
			String exceptionData = getExceptionData_T_M(targetId, memberId);
			throw new DataNotFoundException(exceptionData);
		}
		return source.get();
	}

	private String getExceptionData_T_M(Long targetId, Long memberId) {
		Map<String, Long> dataSource = new HashMap<>();
		dataSource.put("memberId", memberId);
		dataSource.put("targetId", targetId);
		return jsonConverter.toJson(dataSource);
	}
}
