package com.hiit.api.domain.dao.it.in;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.repository.dao.bussiness.InItRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InitDaoImpl extends AbstractJpaDao<InItEntity, Long, InItData> implements InItDao {

	private final InItRepository repository;

	public InitDaoImpl(
			JpaRepository<InItEntity, Long> jpaRepository,
			AbstractDataConverter<InItEntity, InItData> converter,
			InItRepository repository) {
		super(jpaRepository, converter);
		this.repository = repository;
	}

	@Override
	public List<InItData> findAllActiveStatusByMember(Long memberId) {
		return findStatusByMember(memberId, ItStatus.ACTIVE);
	}

	@Override
	public List<InItData> findAllEndStatusByMember(Long memberId) {
		return findStatusByMember(memberId, ItStatus.END);
	}

	private List<InItData> findStatusByMember(Long memberId, ItStatus status) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		List<InItEntity> sources = repository.findAllByHiitMemberAndStatus(member, status);
		return sources.stream().map(converter::from).collect(Collectors.toList());
	}

	@Override
	public InItData findActiveStatusByIdAndMember(Long inItId, Long memberId) {
		return findStatusByIdAndMember(memberId, inItId, ItStatus.ACTIVE);
	}

	@Override
	public InItData findEndStatusByIdAndMember(Long inItId, Long memberId) {
		return findStatusByIdAndMember(memberId, inItId, ItStatus.END);
	}

	private InItData findStatusByIdAndMember(Long memberId, Long inItId, ItStatus active) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		Optional<InItEntity> source = repository.findByIdAndHiitMemberAndStatus(inItId, member, active);

		if (source.isEmpty()) {
			throw new DataNotFoundException("InIt id : " + inItId + " Member id : " + memberId);
		}
		return converter.from(source.get());
	}

	@Override
	public InItData findActiveStatusByTargetIdAndMember(Long targetId, Long memberId) {
		return findStatusByTargetIdAndMember(targetId, memberId, ItStatus.ACTIVE);
	}

	@Override
	public InItData findEndStatusByTargetIdAndMember(Long targetId, Long memberId) {
		return findStatusByTargetIdAndMember(targetId, memberId, ItStatus.END);
	}

	private InItData findStatusByTargetIdAndMember(Long targetId, Long memberId, ItStatus status) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		Optional<InItEntity> source =
				repository.findByTargetIdAndStatusAndHiitMember(targetId, status, member);
		if (source.isEmpty()) {
			throw new DataNotFoundException("Target id : " + targetId + " Member id : " + memberId);
		}
		return converter.from(source.get());
	}
}
