package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.end.GetEndItUseCaseRequest;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.ItTimeDetails;
import com.hiit.api.domain.model.it.end.EndItTimeDetails;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
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
public class GetEndItUseCase implements AbstractUseCase<GetEndItUseCaseRequest> {

	private final InItDao dao;
	private final InItEntityConverter entityConverter;
	private final ItTimeDetailsMapper timeDetailsMapper;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional(readOnly = true)
	public EndItInfo execute(final GetEndItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId endInItId = request::getEndInItId;

		InIt source = getSource(memberId, endInItId);
		if (source.isOwner(memberId)) {
			throw new MemberAccessDeniedException(memberId.getId(), endInItId.getId());
		}

		ItTimeDetails timeInfo = extractTimeInfo(source);

		return buildResponse(source, timeInfo);
	}

	private InIt getSource(GetMemberId memberId, GetInItId endInItId) {
		Optional<InItEntity> source =
				dao.findEndStatusByIdAndMember(endInItId.getId(), memberId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetInItId.endKey, endInItId.getId());
			exceptionSource = logSourceGenerator.add(exceptionSource, GetMemberId.key, memberId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		String info = source.get().getInfo();
		ItTimeDetails timeInfo = timeDetailsMapper.read(info, InItTimeDetails.class);
		return entityConverter.from(source.get(), timeInfo);
	}

	private EndItInfo buildResponse(InIt source, ItTimeDetails timeInfo) {
		return EndItInfo.builder()
				.id(source.getId())
				.title(source.getTitle())
				.startTime(timeInfo.getStartTime())
				.endTime(timeInfo.getEndTime())
				.build();
	}

	private ItTimeDetails extractTimeInfo(InIt inIt) {
		ItTimeDetails source = inIt.getTime();
		return EndItTimeDetails.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}
}
