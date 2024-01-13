package com.hiit.api.domain.dao.member;

import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.repository.dao.bussiness.HiitMemberRepository;
import com.hiit.api.repository.dao.document.MemberStatDocRepository;
import com.hiit.api.repository.document.member.ItWithStat;
import com.hiit.api.repository.document.member.MemberStatDoc;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl extends AbstractJpaDao<HiitMemberEntity, Long> implements MemberDao {

	private final HiitMemberRepository repository;
	private final MemberStatDocRepository memberStatDocRepository;

	public MemberDaoImpl(
			JpaRepository<HiitMemberEntity, Long> jpaRepository,
			HiitMemberRepository repository,
			MemberStatDocRepository memberStatDocRepository) {
		super(jpaRepository);
		this.repository = repository;
		this.memberStatDocRepository = memberStatDocRepository;
	}

	@Override
	public Optional<HiitMemberEntity> findByCertificationId(String certificationId) {
		return repository.findByCertificationId(certificationId);
	}

	@Override
	public Optional<MemberStatDoc> findMemberStatDocByMemberId(Long memberId) {
		return memberStatDocRepository.findByMemberId(memberId);
	}

	@Override
	public MemberStatDoc saveMemberStatDoc(MemberStatDoc memberStatDoc) {
		return memberStatDocRepository.save(memberStatDoc);
	}

	@Override
	public Optional<ItWithStat> findItWithStatByMemberIdAndInItId(Long memberId, Long inItId) {
		Optional<MemberStatDoc> docs = memberStatDocRepository.findById(memberId.toString());
		if (docs.isEmpty()) {
			return Optional.empty();
		}
		MemberStatDoc source = docs.get();
		return source.getResource().getItWithCountStats(inItId);
	}
}
