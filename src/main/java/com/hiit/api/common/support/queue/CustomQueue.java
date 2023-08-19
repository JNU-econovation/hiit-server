package com.hiit.api.common.support.queue;

public interface CustomQueue {

	/**
	 * 동시에 동일한 id를 요청시 구분을 위해 추가한 인덱스를 추가하여 id에 해당하는 큐에 넣는다.
	 *
	 * @param id 요청 id
	 * @return 큐 파라미터 객체
	 */
	QueueParam add(Long id);

	/**
	 * id에 해당하는 큐의 맨 앞에 있는 요청이 현재 요청인지 확인한다.
	 *
	 * @param queueParam 큐 요청 파라미터
	 * @return 현재 요청이 맨 앞에 있는지 여부
	 */
	boolean isReady(QueueParam queueParam);

	/**
	 * id에 해당하는 큐에서 맨 앞에 있는 요청을 제거한다.
	 *
	 * @param queueParam 큐 요청 파라미터
	 */
	void poll(QueueParam queueParam);
}
