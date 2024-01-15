package com.hiit.api.domain.usecase.member.listener;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.usecase.with.event.CreateWithEvent;
import com.hiit.api.domain.usecase.with.event.DeleteWithEvent;
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
@RequiredArgsConstructor
public class UpdateItWithStatsEventListener {

	private final MemberDao dao;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@TransactionalEventListener
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(CreateWithEvent event) {
		final Long memberId = event.getMemberId();
		final Long inItId = event.getInItId();
		Optional<MemberStatDoc> source = dao.findMemberStatDocByMemberId(memberId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate(GetMemberId.key, memberId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		MemberStatDoc memberStatDoc = source.get();
		MemberStat stat = memberStatDoc.getResource();
		stat.createWith(inItId);
		dao.saveMemberStatDoc(memberStatDoc);
	}

	@TransactionalEventListener
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(DeleteWithEvent event) {
		Long memberId = event.getMemberId();
		Long inItId = event.getInItId();
		Optional<MemberStatDoc> source = dao.findMemberStatDocByMemberId(memberId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate(GetMemberId.key, memberId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		MemberStatDoc memberStatDoc = source.get();
		MemberStat stat = memberStatDoc.getResource();
		stat.deleteWith(inItId);
		dao.saveMemberStatDoc(memberStatDoc);
	}
}
