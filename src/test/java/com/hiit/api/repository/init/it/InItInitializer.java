package com.hiit.api.repository.init.it;

import com.hiit.api.repository.dao.bussiness.InItRepository;
import com.hiit.api.repository.dao.bussiness.ItRelationRepository;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItType;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class InItInitializer {

	@Autowired InItRepository repository;
	@Autowired ItRelationRepository itRelationRepository;

	private InItEntity data;
	private ItRelationEntity relation;
	private DayCodeList dayCode;

	public void initialize(RegisteredItEntity registeredIt, HiitMemberEntity hiitMember) {
		repository.deleteAllInBatch();
		this.setData(hiitMember, registeredIt);
	}

	private InItEntity setData(HiitMemberEntity hiitMember, RegisteredItEntity registeredIt) {
		InItEntity source = FakeInIt.create(hiitMember);
		this.dayCode = source.getDayCode();
		this.data = repository.save(source);
		ItRelationEntity relation =
				ItRelationEntity.builder()
						.itId(registeredIt.getId())
						.itType(ItType.REGISTERED_IT)
						.inItId(source.getId())
						.build();
		itRelationRepository.save(relation);
		this.relation = relation;
		return this.data;
	}

	public InItEntity addData(HiitMemberEntity hiitMember, RegisteredItEntity registeredIt) {
		InItEntity source = FakeInIt.create(hiitMember);
		InItEntity inIt = repository.save(source);
		ItRelationEntity relation =
				ItRelationEntity.builder()
						.itId(registeredIt.getId())
						.itType(ItType.REGISTERED_IT)
						.inItId(inIt.getId())
						.build();
		return inIt;
	}

	public InItEntity addData(
			HiitMemberEntity hiitMember, RegisteredItEntity registeredIt, DayCodeList dayCode) {
		InItEntity source = FakeInIt.create(hiitMember, dayCode);
		ItRelationEntity relation =
				ItRelationEntity.builder()
						.itId(registeredIt.getId())
						.itType(ItType.REGISTERED_IT)
						.inItId(source.getId())
						.build();
		return repository.save(source);
	}

	public InItEntity getData() {
		return data;
	}

	public ItRelationEntity getRelation() {
		return relation;
	}

	public DayCodeList getDayCode() {
		return dayCode;
	}
}
