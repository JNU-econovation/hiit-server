package com.hiit.api.domain.dao.it.in;

import com.hiit.api.repository.dao.bussiness.InItRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InitDaoImpl implements InItDao {

	private final InItRepository repository;

	@Override
	public List<InItEntity> findAllActiveStatusByMemberId(Long memberId) {
		return findStatusByMember(memberId, ItStatus.ACTIVE);
	}

	@Override
	public List<InItEntity> findAllEndStatusByMemberId(Long memberId) {
		return findStatusByMember(memberId, ItStatus.END);
	}

	private List<InItEntity> findStatusByMember(Long memberId, ItStatus status) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		return repository.findAllByHiitMemberAndStatus(member, status);
	}

	@Override
	public Optional<InItEntity> findActiveStatusByIdAndMemberId(Long inItId, Long memberId) {
		return findStatusByIdAndMember(memberId, inItId, ItStatus.ACTIVE);
	}

	@Override
	public Optional<InItEntity> findEndStatusByIdAndMemberId(Long inItId, Long memberId) {
		return findStatusByIdAndMember(memberId, inItId, ItStatus.END);
	}

	private Optional<InItEntity> findStatusByIdAndMember(
			Long memberId, Long inItId, ItStatus active) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		return repository.findByIdAndHiitMemberAndStatus(inItId, member, active);
	}

	@Override
	public Optional<InItEntity> findActiveStatusByItIdAndMemberId(Long itItd, Long memberId) {
		return findStatusByTargetIdAndMember(itItd, memberId, ItStatus.ACTIVE);
	}

	@Override
	public Optional<InItEntity> findEndStatusByItIdAndMemberId(Long itId, Long memberId) {
		return findStatusByTargetIdAndMember(itId, memberId, ItStatus.END);
	}

	private Optional<InItEntity> findStatusByTargetIdAndMember(
			Long targetId, Long memberId, ItStatus status) {
		HiitMemberEntity member = HiitMemberEntity.builder().id(memberId).build();
		return repository.findByHiitMemberAndTargetIdAndStatus(member, targetId, status);
	}

	@Override
	public void endById(Long inItId, String title) {
		repository.endByIdWithItRelation(inItId, title);
	}

	@Override
	public void deleteById(Long inItId) {
		repository.deleteByInItId(inItId);
	}

	@Override
	public InItEntity save(InItEntity source) {
		return repository.save(source);
	}

	@Override
	public Optional<InItEntity> findById(Long inItId) {
		return repository.findById(inItId);
	}
}
