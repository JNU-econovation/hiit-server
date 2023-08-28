package com.hiit.api.batch.service.fooCrawl.step.fail;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FooUnCompleteCrawlListWriter implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> items) {
		log.info("fail items: {}", items);
	}
}
