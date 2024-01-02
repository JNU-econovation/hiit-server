package com.hiit.api.domain.dao.member;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;

public interface MemberDao extends JpaDao<HiitMemberEntity, Long> {}
