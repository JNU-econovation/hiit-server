package com.hiit.api.repository.dao.bussiness;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.hiit.api.repository.AbstractRepositoryTest;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.init.it.InItInitializer;
import com.hiit.api.repository.init.it.InItsInitializer;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMembersInitializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ItRelationRepositoryTest extends AbstractRepositoryTest {

	@Autowired ItRelationRepository repository;
	@Autowired InItRepository inItRepository;

	@Autowired RegisteredItInitializer registeredItInitializer;
	@Autowired InItsInitializer inItsInitializer;
	@Autowired InItInitializer inItInitializer;
	@Autowired HiitMembersInitializer hiitMembersInitializer;

	@BeforeEach
	void setUp() {
		registeredItInitializer.initialize();
	}

	@Test
	@DisplayName("잇에 참여한 멤버의 수를 조회한다.")
	void inIt_member_count_test() {
		// given
		RegisteredItEntity registeredIt = registeredItInitializer.getData();
		int size = 2;
		List<HiitMemberEntity> members = setUpMembers(size);
		setAdditionalInit(registeredIt, members);
		Long id = registeredItInitializer.getData().getId();

		// when
		Long result = repository.countByTargetItId(id);

		// then
		assertThat(result).isEqualTo(size);
	}

	@Test
	@DisplayName("참여 잇의 멤버를 삭제하면 잇 연관 테이블도 삭제된다.(Hard Delete)")
	void inIt_cascade_remove_test() {
		// given
		RegisteredItEntity registeredIt = registeredItInitializer.getData();
		int size = 2;
		List<HiitMemberEntity> members = setUpMembers(size);
		setAdditionalInit(registeredIt, members);

		// when
		List<InItEntity> inItsData = inItsInitializer.getData();
		for (InItEntity data : inItsData) {
			inItRepository.deleteByIdWithItRelation(data.getId());
		}

		Long id = registeredItInitializer.getData().getId();
		Long result = repository.countByTargetItId(id);

		// then
		assertThat(result).isZero();
	}

	private List<HiitMemberEntity> setUpMembers(int count) {
		hiitMembersInitializer.initialize(count);
		List<HiitMemberEntity> members = hiitMembersInitializer.getData();
		return members;
	}

	private void setAdditionalInit(RegisteredItEntity registeredIt, List<HiitMemberEntity> members) {
		Map<Integer, Map<RegisteredItEntity, HiitMemberEntity>> inItSources = new HashMap<>();
		int i = 0;
		for (HiitMemberEntity member : members) {
			inItSources.put(i++, Map.of(registeredIt, member));
		}
		inItsInitializer.initialize(inItSources);
	}
}
