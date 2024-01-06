package com.hiit.api.domain.dao.member;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.repository.document.member.ItWithStat;
import com.hiit.api.repository.document.member.MemberStatDoc;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.Optional;

public interface MemberDao extends JpaDao<HiitMemberEntity, Long> {

	Optional<HiitMemberEntity> findByCertificationId(String certificationId);

	Optional<MemberStatDoc> findMemberStatDocByMemberId(Long memberId);

	Optional<ItWithStat> findItWithStatByMemberIdAndItId(Long memberId, Long itId);

	MemberStatDoc saveMemberStatDoc(MemberStatDoc memberStatDoc);
}
