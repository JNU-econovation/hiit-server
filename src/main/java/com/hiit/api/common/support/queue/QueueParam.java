package com.hiit.api.common.support.queue;

import java.util.Random;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class QueueParam {

	private Long id;
	private Long param;

	public QueueParam(Long id) {
		long random = new Random().nextLong(1000);
		long time = System.nanoTime();
		this.id = id;
		this.param = id + time + random;
	}

	public QueueParam(Long id, Long param) {
		this.id = id;
		this.param = param;
	}
}
