package com.hiit.api.domain.dao.it.in;

import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.dao.bussiness.InItRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InitDaoImpl extends AbstractJpaDao<InItEntity, Long> implements InItDao {

	private final InItRepository repository;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	public InitDaoImpl(
			JpaRepository<InItEntity, Long> jpaRepository,
			InItRepository repository,
			JsonConverter jsonConverter,
			LogSourceGenerator logSourceGenerator) {
		super(jpaRepository);
		this.repository = repository;
		this.jsonConverter = jsonConverter;
		this.logSourceGenerator = logSourceGenerator;
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
	public Optional<InItEntity> findActiveStatusByIdAndMember(Long inItId, Long memberId) {
		return findStatusByIdAndMember(memberId, inItId, ItStatus.ACTIVE);
	}

	@Override
	public Optional<InItEntity> findEndStatusByIdAndMember(Long inItId, Long memberId) {
		return findStatusByIdAndMember(memberId, inItId, ItStatus.END);
	}

	private Optional<InItEntity> findStatusByIdAndMember(
			Long memberId, Long inItId, ItStatus active) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		return repository.findByIdAndHiitMemberAndStatus(inItId, member, active);
	}

	@Override
	public Optional<InItEntity> findActiveStatusByItIdAndMember(Long itItd, Long memberId) {
		return findStatusByTargetIdAndMember(itItd, memberId, ItStatus.ACTIVE);
	}

	@Override
	public Optional<InItEntity> findEndStatusByItIdAndMember(Long itId, Long memberId) {
		return findStatusByTargetIdAndMember(itId, memberId, ItStatus.END);
	}

	private Optional<InItEntity> findStatusByTargetIdAndMember(
			Long targetId, Long memberId, ItStatus status) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		return repository.findByHiitMemberAndTargetIdAndStatus(member, targetId, status);
	}

	@Override
	public void endByIdWithItRelation(Long inItId, String title) {
		repository.endByIdWithItRelation(inItId, title);
	}

	@Override
	public void deleteByIdWithItRelation(Long inItId) {
		repository.deleteByInItId(inItId);
	}
}
