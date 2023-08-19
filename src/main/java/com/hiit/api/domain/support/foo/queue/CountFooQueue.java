package com.hiit.api.domain.support.foo.queue;

import com.hiit.api.common.support.queue.CustomQueue;
import com.hiit.api.common.support.queue.QueueParam;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CountFooQueue implements CustomQueue {

	private ConcurrentMap<Long, Queue<Long>> queueHashMap = new ConcurrentHashMap<>();
	private ConcurrentMap<Long, Boolean> doneSet = new ConcurrentHashMap<>();
	private Random randomGenerator = new Random();

	public QueueParam add(Long id) {
		long random = randomGenerator.nextLong(1000);
		long time = System.nanoTime();
		QueueParam queueParam = new QueueParam(id, time + random);
		Queue<Long> queue =
				queueHashMap.compute(
						queueParam.getId(), (k, v) -> v == null ? new ConcurrentLinkedQueue<>() : v);
		queue.add(queueParam.getParam());
		return queueParam;
	}

	public boolean isReady(QueueParam queueParam) {
		return Objects.equals(queueHashMap.get(queueParam.getId()).peek(), queueParam.getParam());
	}

	public void poll(QueueParam queueParam) {
		Queue<Long> idQueue = queueHashMap.get(queueParam.getId());
		idQueue.poll();
		log.info(
				"[{}] {} queue size is {} and contains {}",
				Thread.currentThread().getName(),
				queueParam.getId(),
				idQueue.size(),
				idQueue);
		if (idQueue.isEmpty()) {
			queueHashMap.remove(queueParam.getId());
		}
	}

	/**
	 * 완료된 요청 id를 완료 목록에 추가한다.
	 *
	 * @param queueParam 큐 요청 파라미터
	 */
	public void done(QueueParam queueParam) {
		doneSet.put(queueParam.getId(), true);
	}

	/**
	 * 완료된 요청 id인지 확인한다.
	 *
	 * @param id 요청 id
	 * @return 완료 여부
	 */
	public boolean isDone(Long id) {
		return doneSet.get(id) != null;
	}
}
