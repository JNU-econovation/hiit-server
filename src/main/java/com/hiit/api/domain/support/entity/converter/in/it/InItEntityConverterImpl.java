package com.hiit.api.domain.support.entity.converter.in.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.domain.model.ItTimeDetails;
import com.hiit.api.domain.model.it.in.DayCodeDetails;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItStatusDetails;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
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
	public InIt from(InItEntity entity, ItRelationEntity relation, ItTimeDetails timeInfo) {
		LocalTime startTime = timeInfo.getStartTime();
		LocalTime endTime = timeInfo.getEndTime();
		InItTimeDetails inItTimeInfo =
				InItTimeDetails.builder().startTime(startTime).endTime(endTime).build();
		return InIt.builder()
				.id(entity.getId())
				.memberId(entity.getHiitMember().getId())
				.itRelationId(relation.getId())
				.itId(relation.getItId())
				.itType(relation.getItType().getType())
				.title(entity.getTitle())
				.resolution(entity.getResolution())
				.dayCode(DayCodeDetails.valueOf(entity.getDayCode().name()))
				.status(InItStatusDetails.valueOf(entity.getStatus().name()))
				.time(inItTimeInfo)
				.createAt(entity.getCreateAt())
				.updateAt(entity.getUpdateAt())
				.build();
	}

	@Override
	public InItEntity to(Long id, InIt data) {
		InItTimeDetails timeInfo = data.getTime();
		String info = null;
		try {
			info = objectMapper.writeValueAsString(timeInfo);
		} catch (JsonProcessingException e) {
			// info에 기존 데이터가 지워질 수 있으니 null로 둔다.
		}
		return InItEntity.builder()
				.id(id)
				.title(data.getTitle())
				.resolution(data.getResolution())
				.dayCode(DayCodeList.of(data.getDayCode().getValue()))
				.status(ItStatus.valueOf(data.getStatus().name()))
				.info(info)
				.hiitMember(HiitMemberEntity.builder().id(data.getMemberId()).build())
				.build();
	}
}
