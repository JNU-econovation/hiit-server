package com.hiit.api.domain.usecase.foo;

import com.hiit.api.config.AppConfig;
import com.hiit.api.domain.dto.response.FooUseCaseResponse;
import com.hiit.api.web.dto.request.SaveFooRequest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = {AppConfig.class})
class CountFooFacadeUseCaseTest {

	@Autowired CountFooFacadeUseCase countFooFacadeUseCase;
	@Autowired SaveFooUseCase forCountSaveFooUseCase;
	@Autowired GetFooByNameUseCase getFooByNameUseCase;

	@Test
	void concurrencyTest() throws InterruptedException {
		// given
		SaveFooRequest request1 = SaveFooRequest.builder().name("test1").count(10L).build();
		FooUseCaseResponse response1 = forCountSaveFooUseCase.execute(request1);
		Long id1 = getFooByNameUseCase.execute(response1.getName()).getId();

		SaveFooRequest request2 = SaveFooRequest.builder().name("test2").count(10L).build();
		FooUseCaseResponse response2 = forCountSaveFooUseCase.execute(request2);
		Long id2 = getFooByNameUseCase.execute(response2.getName()).getId();

		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// when
		for (int i = 0; i < threadCount; i++) {
			if (i % 3 == 0) {
				executorService.submit(
						() -> {
							try {
								countFooFacadeUseCase.execute(id1);
							} finally {
								latch.countDown();
							}
						});
			} else {
				executorService.submit(
						() -> {
							try {
								countFooFacadeUseCase.execute(id2);
							} finally {
								latch.countDown();
							}
						});
			}
		}

		latch.await();

		// then
		Long executedCount1 = getFooByNameUseCase.execute(request1.getName()).getCount();
		Assertions.assertThat(executedCount1).isZero();
		Long executedCount2 = getFooByNameUseCase.execute(request2.getName()).getCount();
		Assertions.assertThat(executedCount2).isZero();
	}
}
