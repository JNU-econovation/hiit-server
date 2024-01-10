package com.hiit.api.domain.support.entity.converter.with;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.domain.model.ItTimeDetails;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.model.with.WithItTimeDetails;
import com.hiit.api.domain.usecase.with.WithEntityConverter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithEntityConverterImpl implements WithEntityConverter {

	private final ObjectMapper objectMapper;

	@Override
	public With from(WithEntity entity) {
		return With.builder()
				.id(entity.getId())
				.inItId(entity.getInIt().getId())
				.memberId(entity.getMemberId())
				.content(entity.getContent())
				.createAt(entity.getCreateAt())
				.updateAt(entity.getUpdateAt())
				.build();
	}

	public With from(WithEntity entity, ItTimeDetails timeInfo) {
		LocalTime startTime = timeInfo.getStartTime();
		LocalTime endTime = timeInfo.getEndTime();
		WithItTimeDetails withItTimeInfo =
				WithItTimeDetails.builder().startTime(startTime).endTime(endTime).build();
		return from(entity).toBuilder().time(withItTimeInfo).build();
	}

	@Override
	public WithEntity to(With data) {
		return WithEntity.builder()
				.content(data.getContent())
				.inIt(InItEntity.builder().id(data.getInItId()).build())
				.memberId(data.getMemberId())
				.build();
	}

	@Override
	public WithEntity to(Long id, With data) {
		return to(data).toBuilder().id(id).build();
	}
}
