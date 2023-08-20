package com.hiit.api.domain.usecase.foo;

import com.hiit.api.common.support.queue.QueueParam;
import com.hiit.api.domain.support.foo.queue.CountFooQueue;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountFooFacadeUseCase {

	private static final Long MAX_WAIT_TIME = 10L;

	private final CountFooService countFooService;
	private final CountFooQueue queue;

	@SneakyThrows
	public Long execute(Long id) {
		// id가 이미 완료된 상태인지 확인
		if (queue.isDone(id)) {
			log.info(
					"[{}] already done before {} is added to queue", Thread.currentThread().getName(), id);
			queue.fail(id);
			throw new RuntimeException("Foo is already done");
		}

		// 큐에 id를 추가하고, 큐에서 해당 id의 인덱스를 가져온다.
		QueueParam queueParam = queue.add(id);

		// ready 상태가 될 때까지 대기한다.
		while (!queue.isReady(queueParam)) {
			// 대기한다.
			Thread.sleep(MAX_WAIT_TIME);

			// 대기 중에 id가 이미 완료되었는지 확인한다.
			if (queue.isDone(id)) {
				log.info(
						"[{}] after {} / {} is added to queue",
						Thread.currentThread().getName(),
						queueParam.getId(),
						queueParam.getParam());
				// 큐에서 id를 제거한다.
				queue.fail(queueParam);
				throw new RuntimeException("Foo is already done");
			}
		}

		try {
			// countFooService를 실행한다.
			Long executed = countFooService.execute(id);
			log.info(
					"[{}] on {} / {} is executed and result is {}",
					Thread.currentThread().getName(),
					queueParam.getId(),
					queueParam.getParam(),
					executed);

			// countFooService가 실행된 결과가 0이면 완료된 것으로 간주한다.
			if (executed == 0) {
				// 큐에서 id가 완료된 것으로 표시한다.
				queue.done(queueParam);
			}

			// countFooService가 실행된 결과를 반환한다.
			return executed;
		} catch (RuntimeException e) {
			queue.fail(queueParam);
			throw e;
		} finally {
			log.info(
					"[{}] {} / {} is on final",
					Thread.currentThread().getName(),
					queueParam.getId(),
					queueParam.getParam());
			queue.poll(queueParam);
		}
	}
}
