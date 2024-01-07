package com.hiit.api.domain.usecase.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.it.EditInItUseCaseRequest;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.in.DayCodeInfo;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EditInItUseCase implements AbstractUseCase<EditInItUseCaseRequest> {

	private final InItDao inItDao;
	private final InItEntityConverter inItEntityConverter;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional
	public AbstractResponse execute(EditInItUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long inItId = request.getInIt();
		final String dayCode = request.getDayCode();
		final String resolution = request.getResolution();

		InIt source = getSource(inItId);
		if (!source.isOwner(memberId)) {
			log.debug("{} is not owner of {}", memberId, inItId);
			throw new MemberAccessDeniedException(memberId, inItId);
		}

		source.updateDayCode(DayCodeInfo.valueOf(dayCode));
		source.updateResolution(resolution);
		inItDao.save(inItEntityConverter.to(source));
		return AbstractResponse.VOID;
	}

	private InIt getSource(Long inItId) {
		Optional<InItEntity> source = inItDao.findById(inItId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("inItId", inItId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		InItEntity inIt = source.get();
		return inItEntityConverter.from(inIt);
	}
}
