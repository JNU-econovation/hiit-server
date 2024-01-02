package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.end.EditEndItUseCaseRequest;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
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
	private final InItEntityConverter entityConverter;

	@Override
	@Transactional
	public AbstractResponse execute(final EditEndItUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long endInItId = request.getEndInItId();

		log.debug("get end init : m - {}, end - {}", memberId, endInItId);
		InIt source = getSource(memberId, endInItId);
		log.debug("origin end init : {}", source);

		EditElements editElements = extractEditElements(request);
		InIt editedSource = editSource(source, editElements);

		log.debug("edit end init : {}", editedSource);
		edit(editedSource);

		return AbstractResponse.VOID;
	}

	private InIt getSource(Long memberId, Long endInItId) {
		return entityConverter.from(inItDao.findEndStatusByIdAndMember(memberId, endInItId));
	}

	private EditElements extractEditElements(EditEndItUseCaseRequest request) {
		return EditElements.builder().title(request.getTitle()).build();
	}

	private InIt editSource(InIt source, EditElements editElements) {
		String title = editElements.getTitle();
		return source.toBuilder().title(title).build();
	}

	private void edit(InIt source) {
		inItDao.save(entityConverter.to(source));
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
