package com.hiit.api.domain.dao.it.in;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import org.springframework.stereotype.Component;

@Component
public class InItDataConverter implements AbstractDataConverter<InItEntity, InItData> {

	@Override
	public InItData from(InItEntity entity) {
		return InItData.builder()
				.id(entity.getId())
				.title(entity.getTitle())
				.resolution(entity.getResolution())
				.dayCode(DayCodeInfo.of(entity.getDayCode().getCode()))
				.status(ItStatusInfo.valueOf(entity.getStatus().name()))
				.memberId(entity.getHiitMember().getId())
				.itRelationId(entity.getItRelationEntity().getId())
				.build();
	}

	public InItEntity to(InItData data) {
		return InItEntity.builder()
				.title(data.getTitle())
				.resolution(data.getResolution())
				.dayCode(DayCodeList.of(data.getDayCode().getCode()))
				.status(ItStatus.valueOf(data.getStatus().name()))
				.build();
	}
}
