package com.hiit.api.domain.support.foo.queue;

import com.google.common.collect.EvictingQueue;
import com.hiit.api.common.support.queue.CustomQueue;
import com.hiit.api.common.support.queue.QueueParam;
import com.hiit.api.repository.dao.log.FooQueueLogRepository;
import com.hiit.api.repository.entity.business.supporter.FooEntitySupporter;
import com.hiit.api.repository.entity.log.FooQueueLogEntity;
import com.hiit.api.repository.entity.log.QueueStatus;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CountFooQueue implements CustomQueue {

	private static EvictingQueue<Long> doneIds = EvictingQueue.create(100);

	private final FooQueueLogRepository fooQueueLogRepository;
	private final FooEntitySupporter fooEntitySupporter;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public QueueParam add(Long id) {
		FooQueueLogEntity queueLog =
				FooQueueLogEntity.builder().editId(fooEntitySupporter.getIdEntity(id)).build();
		FooQueueLogEntity saved = fooQueueLogRepository.saveAndFlush(queueLog);
		return new QueueParam(id, saved.getId());
	}

	@Transactional(readOnly = true)
	public boolean isReady(QueueParam queueParam) {
		FooQueueLogEntity top =
				fooQueueLogRepository
						.findTopByEditIdAndStatusOrderByCreateAt(
								fooEntitySupporter.getIdEntity(queueParam.getId()), QueueStatus.READY)
						.orElseThrow(() -> new RuntimeException("not found"));
		log.info(
				"[{}] isReady id : {}, param : {}, top : {}",
				Thread.currentThread().getName(),
				queueParam.getId(),
				queueParam.getParam(),
				top.getId());
		return Objects.equals(top.getId(), queueParam.getParam());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void poll(QueueParam queueParam) {
		FooQueueLogEntity queueLog =
				fooQueueLogRepository
						.findByIdAndEditId(
								queueParam.getParam(), fooEntitySupporter.getIdEntity(queueParam.getId()))
						.orElseThrow(() -> new RuntimeException("not found"));

		if (queueLog.getStatus() != QueueStatus.READY) {
			return;
		}

		FooQueueLogEntity updateQueueLog = queueLog.toBuilder().status(QueueStatus.SUCCESS).build();
		fooQueueLogRepository.saveAndFlush(updateQueueLog);
	}

	/**
	 * 완료된 요청 id를 완료 목록에 추가한다.
	 *
	 * @param queueParam 큐 요청 파라미터
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void done(QueueParam queueParam) {
		doneIds.add(queueParam.getId());
		FooQueueLogEntity queueLog =
				fooQueueLogRepository
						.findById(queueParam.getParam())
						.orElseThrow(() -> new RuntimeException("not found"));
		FooQueueLogEntity successLog = queueLog.toBuilder().status(QueueStatus.SUCCESS).build();
		fooQueueLogRepository.saveAndFlush(successLog);

		FooQueueLogEntity doneQueueLog =
				FooQueueLogEntity.builder()
						.editId(fooEntitySupporter.getIdEntity(queueParam.getId()))
						.status(QueueStatus.DONE)
						.build();
		fooQueueLogRepository.saveAndFlush(doneQueueLog);
	}

	/**
	 * 완료된 요청 id인지 확인한다.
	 *
	 * @param id 요청 id
	 * @return 완료 여부
	 */
	@Transactional(readOnly = true)
	public boolean isDone(Long id) {
		if (doneIds.contains(id)) {
			log.info("[{}] isDone by doneIds id : {}, done : true", Thread.currentThread().getName(), id);
			return true;
		}

		boolean done =
				fooQueueLogRepository.existsByEditIdAndStatus(
						fooEntitySupporter.getIdEntity(id), QueueStatus.DONE);

		if (done) {
			doneIds.add(id);
		}

		log.info("[{}] isDone by query id : {}, done : {}", Thread.currentThread().getName(), id, done);
		return done;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void fail(Long id) {
		FooQueueLogEntity failQueueLog =
				FooQueueLogEntity.builder()
						.editId(fooEntitySupporter.getIdEntity(id))
						.status(QueueStatus.FAIL)
						.build();
		fooQueueLogRepository.save(failQueueLog);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void fail(QueueParam queueParam) {
		FooQueueLogEntity queueLog =
				fooQueueLogRepository
						.findById(queueParam.getParam())
						.orElseThrow(() -> new RuntimeException("not found"));
		FooQueueLogEntity failQueueLog = queueLog.toBuilder().status(QueueStatus.FAIL).build();
		fooQueueLogRepository.save(failQueueLog);
	}
}
