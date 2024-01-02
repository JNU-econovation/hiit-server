package com.hiit.api.domain.support.entity.converter.in.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.domain.model.ItTimeInfo;
import com.hiit.api.domain.model.it.in.DayCodeInfo;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeInfo;
import com.hiit.api.domain.model.it.in.ItStatusInfo;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InItEntityConverterImpl implements InItEntityConverter {

	private final ObjectMapper objectMapper;

	@Override
	public InIt from(InItEntity entity) {
		return InIt.builder()
				.id(entity.getId())
				.memberId(entity.getHiitMember().getId())
				.itRelationId(entity.getItRelationEntity().getId())
				.title(entity.getTitle())
				.resolution(entity.getResolution())
				.dayCode(DayCodeInfo.valueOf(entity.getDayCode().name()))
				.status(ItStatusInfo.valueOf(entity.getStatus().name()))
				.createAt(entity.getCreateAt())
				.updateAt(entity.getUpdateAt())
				.build();
	}

	@Override
	public InIt from(InItEntity entity, ItTimeInfo timeInfo) {
		LocalTime startTime = timeInfo.getStartTime();
		LocalTime endTime = timeInfo.getEndTime();
		InItTimeInfo inItTimeInfo =
				InItTimeInfo.builder().startTime(startTime).endTime(endTime).build();
		return from(entity).toBuilder().timeInfo(inItTimeInfo).build();
	}

	@Override
	public InItEntity to(InIt data) {
		InItTimeInfo timeInfo = data.getTimeInfo();
		String info = null;
		try {
			info = objectMapper.writeValueAsString(timeInfo);
		} catch (JsonProcessingException e) {
			// info에 기존 데이터가 지워질 수 있으니 null로 둔다.
		}
		return InItEntity.builder()
				.title(data.getTitle())
				.resolution(data.getResolution())
				.dayCode(DayCodeList.of(data.getDayCode().getCode()))
				.status(ItStatus.valueOf(data.getStatus().name()))
				.info(info)
				.build();
	}

	@Override
	public InItEntity to(Long id, InIt data) {
		return to(data).toBuilder().id(id).build();
	}
}
