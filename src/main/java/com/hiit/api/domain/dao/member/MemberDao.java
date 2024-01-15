package com.hiit.api.domain.dao.member;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.repository.document.member.ItWithStat;
import com.hiit.api.repository.document.member.MemberStatDoc;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.member.MemberNotificationInfoEntity;
import java.util.List;
import java.util.Optional;

public interface MemberDao extends JpaDao<HiitMemberEntity, Long> {

	Optional<HiitMemberEntity> findByCertificationId(String certificationId);

	Optional<MemberStatDoc> findMemberStatDocByMemberId(Long memberId);

	Optional<ItWithStat> findItWithStatByMemberIdAndInItId(Long memberId, Long inItId);

	MemberStatDoc saveMemberStatDoc(MemberStatDoc memberStatDoc);

	MemberNotificationInfoEntity saveNotificationInfo(MemberNotificationInfoEntity info);

	List<MemberNotificationInfoEntity> findAllNotificationInfoByHiitMemberEntity(
			HiitMemberEntity hiitMemberEntity);
}
