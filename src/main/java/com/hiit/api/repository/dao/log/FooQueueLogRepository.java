package com.hiit.api.repository.dao.log;

import com.hiit.api.repository.entity.business.FooEntity;
import com.hiit.api.repository.entity.log.FooQueueLogEntity;
import com.hiit.api.repository.entity.log.QueueStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooQueueLogRepository extends JpaRepository<FooQueueLogEntity, Long> {

	Optional<FooQueueLogEntity> findByIdAndEditId(Long id, FooEntity editFooId);

	Optional<FooQueueLogEntity> findTopByEditIdAndStatusOrderByCreateAt(
			FooEntity editFooId, QueueStatus status);

	boolean existsByEditIdAndStatus(FooEntity editFooId, QueueStatus status);
}
