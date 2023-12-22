package com.hiit.api.domain.service.manager.it.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.hiit.api.domain.dao.it.relation.TargetItTypeInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ItTimeInfoExecutorManagerTest {

	@Autowired private ItTimeInfoExecutorManager itTimeInfoExecutorManager;

	@Test
	@DisplayName("등록된 잇의 시간 조회 실행자를 가져온다.")
	void getExecutor() {
		// given
		TargetItTypeInfo typeInfo = TargetItTypeInfo.REGISTERED_IT;

		// when
		ItTimeInfoExecutor executor = itTimeInfoExecutorManager.getExecutor(typeInfo);

		// then
		assertNotNull(executor);
		assertThat(executor).isInstanceOf(RegisteredItTimeInfoExecutor.class);
	}

	@Test
	@DisplayName("잘못된 시간 조회 실행자를 가져온다.")
	void getExecutor_invalid_type() {
		// given
		TargetItTypeInfo typeInfo = TargetItTypeInfo.FOR_TEST;

		// when & then
		Assertions.assertThatThrownBy(() -> itTimeInfoExecutorManager.getExecutor(typeInfo))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("not exist executor for targetType : ");
	}
}
