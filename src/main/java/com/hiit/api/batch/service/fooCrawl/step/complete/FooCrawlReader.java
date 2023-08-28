package com.hiit.api.batch.service.fooCrawl.step.complete;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FooCrawlReader implements ItemReader<String> {
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
		getCrawlList();
	}

	private void getCrawlList() {
		crawlList.add("www.google.com");
		crawlList.add("www.naver.com");
		crawlList.add("www.daum.net");
		crawlList.add("www.kakao.com");
		crawlList.add("www.nate.com");
		crawlList.add("www.bing.com");
	}
}
