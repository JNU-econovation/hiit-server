package com.hiit.api.repository.repository.bussiness.jpa;

import com.hiit.api.repository.entity.business.FooEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = FooEntity.class, idClass = Long.class)
public interface FooJpaRepository extends JpaRepository<FooEntity, Long> {

	/** 하나의 결과를 원하고 조건을 통해 유일성을 보장할 수 없는 경우는 Top 키워드를 통해 최상위 하나의 결과를 반환하도록 한다. */
	Optional<FooEntity> findTopByNameAndDeletedFalseOrderByIdDesc(String name);

	/** 여러 개의 결과를 조회하는 경우는 정렬 후 반환한다. */
	List<FooEntity> findAllByNameAndDeletedFalseOrderByIdDesc(String name);
}
