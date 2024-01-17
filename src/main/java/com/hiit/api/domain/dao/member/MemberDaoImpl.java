package com.hiit.api.domain.dao.member;

import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.repository.dao.bussiness.HiitMemberRepository;
import com.hiit.api.repository.dao.bussiness.MemberNotificationInfoRepository;
import com.hiit.api.repository.dao.document.MemberStatDocRepository;
import com.hiit.api.repository.document.member.ItWithStat;
import com.hiit.api.repository.document.member.MemberStatDoc;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.member.MemberNotificationInfoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl extends AbstractJpaDao<HiitMemberEntity, Long> implements MemberDao {

	private final HiitMemberRepository repository;
	private final MemberStatDocRepository memberStatDocRepository;

	private final MemberNotificationInfoRepository memberNotificationInfoRepository;

	public MemberDaoImpl(
			JpaRepository<HiitMemberEntity, Long> jpaRepository,
			HiitMemberRepository repository,
			MemberStatDocRepository memberStatDocRepository,
			MemberNotificationInfoRepository memberNotificationInfoRepository) {
		super(jpaRepository);
		this.repository = repository;
		this.memberStatDocRepository = memberStatDocRepository;
		this.memberNotificationInfoRepository = memberNotificationInfoRepository;
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
		Optional<MemberStatDoc> docs = memberStatDocRepository.findByMemberId(memberId);
		if (docs.isEmpty()) {
			return Optional.empty();
		}
		MemberStatDoc source = docs.get();
		return source.getResource().getItWithCountStats(inItId);
	}

	@Override
	public MemberNotificationInfoEntity saveNotificationInfo(MemberNotificationInfoEntity info) {
		return memberNotificationInfoRepository.save(info);
	}

	@Override
	public List<MemberNotificationInfoEntity> findAllNotificationInfoByHiitMemberEntity(
			HiitMemberEntity hiitMemberEntity) {
		return memberNotificationInfoRepository.findAllByHiitMemberEntity(hiitMemberEntity);
	}

	@Override
	public void deleteNotificationInfos(List<MemberNotificationInfoEntity> infos) {
		memberNotificationInfoRepository.deleteAll(infos);
	}
}
