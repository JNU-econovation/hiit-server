package com.hiit.api.domain.dao.member;

import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.repository.dao.bussiness.HiitMemberRepository;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl extends AbstractJpaDao<HiitMemberEntity, Long> implements MemberDao {

	private final HiitMemberRepository repository;

	public MemberDaoImpl(
			JpaRepository<HiitMemberEntity, Long> jpaRepository, HiitMemberRepository repository) {
		super(jpaRepository);
		this.repository = repository;
	}
}
