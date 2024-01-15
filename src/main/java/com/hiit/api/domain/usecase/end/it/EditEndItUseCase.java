package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.end.EditEndItUseCaseRequest;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.member.MemberQuery;
import com.hiit.api.domain.support.entity.converter.in.it.InItEntityConverterImpl;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.TargetItType;
import java.util.Map;
import java.util.Optional;
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

	private final InItDao dao;
	private final InItEntityConverterImpl entityConverter;

	private final MemberQuery memberQuery;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional
	public AbstractResponse execute(final EditEndItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId endInItId = request::getEndInItId;

		GetMemberId member = memberQuery.query(memberId);
		InItElement element = getSource(member, endInItId);
		InIt source = element.getSource();
		if (!source.isOwner(member)) {
			throw new MemberAccessDeniedException(member.getId(), endInItId.getId());
		}

		EditElements editElements = extractEditElements(request);
		InIt editedSource = editSource(source, editElements);

		edit(editedSource, element);
		return AbstractResponse.VOID;
	}

	private InItElement getSource(GetMemberId memberId, GetInItId endInItId) {
		Optional<InItEntity> source =
				dao.findActiveStatusByIdAndMember(endInItId.getId(), memberId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetInItId.endKey, endInItId.getId());
			exceptionSource = logSourceGenerator.add(exceptionSource, GetMemberId.key, memberId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		InItEntity inIt = source.get();
		return InItElement.builder()
				.source(entityConverter.from(inIt))
				.targetItId(inIt.getItRelationEntity().getTargetItId())
				.targetItType(inIt.getItRelationEntity().getTargetItType())
				.build();
	}

	private EditElements extractEditElements(EditEndItUseCaseRequest request) {
		return EditElements.builder().title(request.getTitle()).build();
	}

	private InIt editSource(InIt source, EditElements editElements) {
		String title = editElements.getTitle();
		return source.toBuilder().title(title).build();
	}

	private void edit(InIt source, InItElement element) {
		dao.save(
				entityConverter.to(
						source.getId(), source, element.getTargetItId(), element.getTargetItType()));
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

	@Getter
	@ToString
	@EqualsAndHashCode
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder(toBuilder = true)
	private static class InItElement {

		private InIt source;
		private Long targetItId;
		private TargetItType targetItType;
	}
}
