package com.hiit.api.domain.service.with;

import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.with.GetWithId;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.model.with.WithItTimeDetails;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.usecase.with.WithEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WithQueryImpl implements WithQuery {

	private final WithDao withDao;
	private final WithEntityConverter withEntityConverter;
	private final ItTimeDetailsMapper itTimeDetailsMapper;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional(readOnly = true)
	public With query(GetWithId withId) {
		Optional<WithEntity> source = withDao.findById(withId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetWithId.key, withId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		String info = source.get().getInIt().getInfo();
		WithItTimeDetails timeDetails = itTimeDetailsMapper.read(info, WithItTimeDetails.class);
		return withEntityConverter.from(source.get(), timeDetails);
	}
}
