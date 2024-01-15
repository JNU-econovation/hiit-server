package com.hiit.api.domain.usecase.member.listener;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.usecase.member.event.CreateMemberEvent;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.document.member.MemberStat;
import com.hiit.api.repository.document.member.MemberStatDoc;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class CreateMemberStatDocEventListener {

	private final MemberDao dao;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@TransactionalEventListener
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(CreateMemberEvent event) {
		final Long memberId = event.getMemberId();
		Optional<MemberStatDoc> source = dao.findMemberStatDocByMemberId(memberId);
		if (source.isPresent()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate(GetMemberId.key, memberId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		MemberStat memberStat = MemberStat.builder().memberId(memberId).build();
		MemberStatDoc doc = MemberStatDoc.builder().memberId(memberId).resource(memberStat).build();
		dao.saveMemberStatDoc(doc);
	}
}
