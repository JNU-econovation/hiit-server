package com.hiit.api.repository.init.it;

import com.hiit.api.repository.dao.bussiness.InItRepository;
import com.hiit.api.repository.dao.bussiness.ItRelationRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import com.hiit.api.repository.entity.business.it.TargetItType;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class InItsInitializer {

	@Autowired InItRepository repository;
	@Autowired ItRelationRepository itRelationRepository;

	private List<InItEntity> data = new ArrayList<>();
	private List<ItRelationEntity> relations = new ArrayList<>();

	public void initialize(Map<Integer, Map<RegisteredItEntity, HiitMemberEntity>> sources) {
		//		repository.deleteAll();
		for (Map.Entry<Integer, Map<RegisteredItEntity, HiitMemberEntity>> source :
				sources.entrySet()) {
			for (Map.Entry<RegisteredItEntity, HiitMemberEntity> entry : source.getValue().entrySet()) {
				InItEntity inIt = this.setData(entry.getValue(), entry.getKey());
				data.add(inIt);
			}
		}
	}

	private InItEntity setData(HiitMemberEntity hiitMember, RegisteredItEntity registeredIt) {
		InItEntity source = FakeInIt.create(hiitMember);
		ItRelationEntity relation =
				ItRelationEntity.builder()
						.targetItId(registeredIt.getId())
						.targetItType(TargetItType.REGISTERED_IT)
						.inIt(source)
						.build();
		source.associate(relation);
		this.relations.add(relation);
		return repository.save(source);
	}

	public List<InItEntity> getData() {
		return data;
	}

	public List<ItRelationEntity> getRelations() {
		return relations;
	}
}
