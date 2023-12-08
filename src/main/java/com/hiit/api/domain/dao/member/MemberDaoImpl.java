package com.hiit.api.domain.dao.member;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.repository.dao.bussiness.HiitMemberRepository;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl extends AbstractJpaDao<HiitMemberEntity, Long, MemberData>
		implements MemberDao {

	private final HiitMemberRepository repository;

	public MemberDaoImpl(
			JpaRepository<HiitMemberEntity, Long> jpaRepository,
			AbstractDataConverter<HiitMemberEntity, MemberData> converter,
			HiitMemberRepository repository) {
		super(jpaRepository, converter);
		this.repository = repository;
	}
}
