package com.hiit.api.repository.dao.bussiness.jpa;

import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RegisteredItJpaRepository extends JpaRepository<RegisteredItEntity, Long> {}
