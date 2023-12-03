package com.hiit.api.repository.init.member;

import com.hiit.api.repository.dao.bussiness.HiitMemberRepository;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class HiitMembersInitializer {

	@Autowired HiitMemberRepository repository;

	private List<HiitMemberEntity> data;

	public void initialize(int i) {
		repository.deleteAll();
		this.setData(i);
	}

	private List<HiitMemberEntity> setData(int i) {
		List<HiitMemberEntity> source = FakeHiitMember.createMany(i);
		repository.saveAll(source);
		this.data = source;
		return this.data;
	}

	public List<HiitMemberEntity> getData() {
		return data;
	}
}
