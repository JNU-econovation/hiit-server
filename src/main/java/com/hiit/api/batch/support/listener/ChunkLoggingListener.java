package com.hiit.api.batch.support.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/** Chunk 리스너 */
@Slf4j
@Component
public class ChunkLoggingListener implements ChunkListener {

	@Override
	public void beforeChunk(ChunkContext context) {}

	@Override
	public void afterChunk(ChunkContext context) {}

	@Override
	public void afterChunkError(ChunkContext context) {}
}
