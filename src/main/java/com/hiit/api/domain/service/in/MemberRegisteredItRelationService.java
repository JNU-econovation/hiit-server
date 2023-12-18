package com.hiit.api.domain.service.in;

import static com.hiit.api.domain.dao.it.relation.TargetItTypeInfo.REGISTERED_IT;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.it.relation.ItRelationData;
import com.hiit.api.domain.exception.DataNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRegisteredItRelationService {

	private final InItDao initDao;
	private final ItRelationDao itRelationDao;

	public List<ItRelationData> browse(Long memberId) {
		List<ItRelationData> source = new ArrayList<>();
		List<InItData> memberInIts = getInItData(memberId);
		for (InItData memberInIt : memberInIts) {
			ItRelationData itRelation = getItRelation(memberInIt);
			if (itRelation.isType(REGISTERED_IT)) {
				source.add(itRelation);
			}
		}
		return source;
	}

	private List<InItData> getInItData(Long memberId) {
		return initDao.findAllActiveStatusByMember(memberId);
	}

	private ItRelationData getItRelation(InItData memberInIt) {
		return itRelationDao
				.findById(memberInIt.getItRelationId())
				.orElseThrow(
						() -> new DataNotFoundException("itRelation : " + memberInIt.getItRelationId()));
	}
}
