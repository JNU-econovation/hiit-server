package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.end.DeleteEndItUseCaseRequest;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteEndItUseCase implements AbstractUseCase<DeleteEndItUseCaseRequest> {

	private final InItDao inItDao;
	private final InItEntityConverter entityConverter;

	@Override
	@Transactional
	public AbstractResponse execute(final DeleteEndItUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long endInItId = request.getEndInItId();

		log.debug("get end init : m - {}, end - {}", memberId, endInItId);
		InIt source = getSource(memberId, endInItId);

		log.debug("delete end init : {}", source);
		delete(source);

		return AbstractResponse.VOID;
	}

	private InIt getSource(Long memberId, Long endInItId) {
		return entityConverter.from(inItDao.findEndStatusByIdAndMember(memberId, endInItId));
	}

	private void delete(InIt source) {
		inItDao.delete(entityConverter.to(source));
	}
}
