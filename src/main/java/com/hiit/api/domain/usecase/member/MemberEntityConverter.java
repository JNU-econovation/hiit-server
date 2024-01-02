package com.hiit.api.domain.usecase.member;

import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.support.AbstractEntityConverter;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;

public interface MemberEntityConverter extends AbstractEntityConverter<HiitMemberEntity, Member> {}
