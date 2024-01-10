package com.hiit.api.domain.service.it.in;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItStatusDetails;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ActiveInItTimeDetailsQueryImpl implements InItTimeDetailsQuery {

	private final InItDao inItDao;
	private final InItEntityConverter inItEntityConverter;

	private final ItTimeDetailsMapper itTimeDetailsMapper;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional(readOnly = true)
	public InIt query(GetInItId inItId, GetMemberId memberId) {
		Optional<InItEntity> source =
				inItDao.findActiveStatusByIdAndMember(inItId.getId(), memberId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate(inItId.key, inItId.getId());
			exceptionSource = logSourceGenerator.add(exceptionSource, memberId.key, memberId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		String info = source.get().getInfo();
		InItTimeDetails timeInfo = itTimeDetailsMapper.read(info, InItTimeDetails.class);
		return inItEntityConverter.from(source.get(), timeInfo);
	}

	@Override
	public InItStatusDetails getStatus() {
		return InItStatusDetails.ACTIVE;
	}
}
