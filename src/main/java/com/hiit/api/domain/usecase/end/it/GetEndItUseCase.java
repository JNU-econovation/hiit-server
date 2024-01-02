package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.end.GetEndItUseCaseRequest;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.model.ItTimeInfo;
import com.hiit.api.domain.model.it.end.EndItTimeInfo;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeInfo;
import com.hiit.api.domain.service.ItTimeInfoMapper;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetEndItUseCase implements AbstractUseCase<GetEndItUseCaseRequest> {

	private final InItDao inItDao;
	private final InItEntityConverter entityConverter;

	private final ItTimeInfoMapper itTimeInfoMapper;

	@Override
	@Transactional(readOnly = true)
	public EndItInfo execute(final GetEndItUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long endInItId = request.getEndInItId();

		log.debug("get end init : m - {}, end - {}", memberId, endInItId);
		InIt source = getSource(memberId, endInItId);

		ItTimeInfo timeInfo = extractTimeInfo(source);

		return buildResponse(source, timeInfo);
	}

	private InIt getSource(Long memberId, Long endInItId) {
		InItEntity source = inItDao.findEndStatusByIdAndMember(memberId, endInItId);
		String info = source.getInfo();
		log.debug("convert init info to time info : m - {}, end - {}", memberId, endInItId);
		ItTimeInfo timeInfo = itTimeInfoMapper.read(info, InItTimeInfo.class);
		return entityConverter.from(source, timeInfo);
	}

	private EndItInfo buildResponse(InIt source, ItTimeInfo timeInfo) {
		return EndItInfo.builder()
				.id(source.getId())
				.title(source.getTitle())
				.startTime(timeInfo.getStartTime())
				.endTime(timeInfo.getEndTime())
				.build();
	}

	private ItTimeInfo extractTimeInfo(InIt inIt) {
		ItTimeInfo source = inIt.getTimeInfo();
		return EndItTimeInfo.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}
}
