package com.hiit.api.repository.dao.bussiness.jpa;

import com.hiit.api.repository.entity.business.BarEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BarJpaRepository extends JpaRepository<BarEntity, Long> {
	Optional<BarEntity> findByIdAndDeletedFalse(Long id);
}
