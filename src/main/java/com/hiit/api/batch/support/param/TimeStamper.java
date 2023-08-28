package com.hiit.api.batch.support.param;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.stereotype.Component;

/** Job Parameter Incrementer */
@Component
public class TimeStamper implements JobParametersIncrementer {
	static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");

	/**
	 * timestamp를 Job Parameter에 추가한다.
	 *
	 * @param parameters Job Parameter
	 * @return timestamp가 추가된 Job Parameter
	 */
	@Override
	public JobParameters getNext(JobParameters parameters) {
		String timeStamp = dateFormat.format(new Date());

		return new JobParametersBuilder(parameters).addString("timeStamp", timeStamp).toJobParameters();
	}
}
