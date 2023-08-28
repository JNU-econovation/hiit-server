package com.hiit.api.batch.service.fooCrawl.step.fail;

import com.hiit.api.batch.support.converter.StringListConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FooUnCompleteCrawlReader implements ItemReader<String> {
	public static List<String> crawlList = new ArrayList<>();

	@Override
	public String read() {
		return getElement();
	}

	private String getElement() {
		if (crawlList.isEmpty()) {
			return null;
		}
		return crawlList.remove(0);
	}

	@BeforeStep
	public void beforeStep(StepExecution execution) {
		Object failList = execution.getJobExecution().getExecutionContext().get("failList");
		if (Objects.isNull(failList)) {
			throw new RuntimeException("empty failList");
		}
		List<String> list = StringListConverter.toStringList(failList.toString());
		crawlList.addAll(list);
	}
}
