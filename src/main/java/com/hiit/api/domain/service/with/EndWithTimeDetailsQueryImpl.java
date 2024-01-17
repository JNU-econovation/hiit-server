package com.hiit.api.domain.service.with;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InItStatusDetails;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.with.GetWithId;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.usecase.with.WithEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EndWithTimeDetailsQueryImpl implements WithTimeDetailsQuery {

	private final WithDao withDao;
	private final WithEntityConverter withEntityConverter;

	private final InItDao inItDao;

	private final ItTimeDetailsMapper itTimeDetailsMapper;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional(readOnly = true)
	public With query(GetWithId withId, GetMemberId memberId) {
		Optional<WithEntity> source = withDao.findById(withId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate(withId.key, withId.getId());
			exceptionSource = logSourceGenerator.add(exceptionSource, memberId.key, memberId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		Optional<InItEntity> inItEntity =
				inItDao.findEndStatusByIdAndMember(source.get().getInIt().getId(), memberId.getId());
		if (inItEntity.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetInItId.key, source.get().getInIt().getId());
			exceptionSource = logSourceGenerator.add(exceptionSource, memberId.key, memberId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		String info = inItEntity.get().getInfo();
		InItTimeDetails timeInfo = itTimeDetailsMapper.read(info, InItTimeDetails.class);
		return withEntityConverter.from(source.get(), timeInfo);
	}

	@Override
	public InItStatusDetails getStatus() {
		return InItStatusDetails.END;
	}
}
