package com.hiit.api.domain.dao.it.registerd;

import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.repository.dao.bussiness.RegisteredItRepository;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RegisteredItDaoImpl extends AbstractJpaDao<RegisteredItEntity, Long>
		implements RegisteredItDao {

	private final RegisteredItRepository repository;

	public RegisteredItDaoImpl(
			JpaRepository<RegisteredItEntity, Long> jpaRepository, RegisteredItRepository repository) {
		super(jpaRepository);
		this.repository = repository;
	}
}
