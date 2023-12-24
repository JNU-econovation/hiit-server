package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dto.request.end.DeleteEndItUseCaseRequest;
import com.hiit.api.domain.usecase.AbstractUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteEndItUseCase implements AbstractUseCase<DeleteEndItUseCaseRequest> {

	private final InItDao inItDao;

	@Override
	@Transactional
	public ServiceResponse execute(DeleteEndItUseCaseRequest request) {
		Long memberId = request.getMemberId();
		Long endInItId = request.getEndInItId();

		InItData source = getEditEndInIt(memberId, endInItId);

		inItDao.delete(source);

		return ServiceResponse.VOID;
	}

	private InItData getEditEndInIt(Long memberId, Long endInIt) {
		return inItDao.findEndStatusByIdAndMember(memberId, endInIt);
	}
}
