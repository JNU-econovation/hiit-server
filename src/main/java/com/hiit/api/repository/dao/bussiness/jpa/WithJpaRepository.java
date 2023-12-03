package com.hiit.api.repository.dao.bussiness.jpa;

import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface WithJpaRepository extends JpaRepository<WithEntity, Long> {

	/** 특정 잇의 with 개수를 반환한다. */
	Long countByInIt(InItEntity inIt);

	/** 특정 윗이 존재하는지 여부를 반환한다. */
	boolean existsById(Long id);
}
