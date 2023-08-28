package com.hiit.api.batch.service.fooCrawl.step.complete;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FooCrawlProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		return item;
	}
}
