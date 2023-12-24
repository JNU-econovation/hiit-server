package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dto.request.end.EditEndItUseCaseRequest;
import com.hiit.api.domain.usecase.AbstractUseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EditEndItUseCase implements AbstractUseCase<EditEndItUseCaseRequest> {

	private final InItDao inItDao;

	@Override
	@Transactional
	public ServiceResponse execute(EditEndItUseCaseRequest request) {
		Long memberId = request.getMemberId();
		Long endInItId = request.getEndInItId();

		InItData source = getEditEndInIt(memberId, endInItId);

		EditElements editElements = extractEditElements(request);

		InItData editedSource = editSource(source, editElements);
		inItDao.save(editedSource);

		return ServiceResponse.VOID;
	}

	private InItData getEditEndInIt(Long memberId, Long endInIt) {
		return inItDao.findEndStatusByIdAndMember(memberId, endInIt);
	}

	private EditElements extractEditElements(EditEndItUseCaseRequest request) {
		return EditElements.builder().title(request.getTitle()).build();
	}

	private InItData editSource(InItData source, EditElements editElements) {
		String title = editElements.getTitle();
		return source.toBuilder().title(title).build();
	}

	@Getter
	@ToString
	@EqualsAndHashCode
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder(toBuilder = true)
	private static class EditElements {
		private String title;
	}
}
