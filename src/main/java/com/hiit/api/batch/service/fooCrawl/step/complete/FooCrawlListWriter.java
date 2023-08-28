package com.hiit.api.batch.service.fooCrawl.step.complete;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FooCrawlListWriter implements ItemWriter<String> {

	private Random random = new Random();
	private List<String> failList = new ArrayList<>();

	@Override
	public void write(List<? extends String> items) {
		int nextInt = random.nextInt(items.size());
		log.info("items: {}", items);
		for (String item : items) {
			if (item.equals(items.get(nextInt)) && nextInt % 2 == 0) {
				log.info("fail item: {}", item);
				failList.add(item);
			}
		}
	}

	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (!failList.isEmpty()) {
			stepExecution.setExitStatus(ExitStatus.FAILED);
			stepExecution.getJobExecution().getExecutionContext().put("failList", failList.toString());
		}
		failList.clear();
		return stepExecution.getExitStatus();
	}
}
