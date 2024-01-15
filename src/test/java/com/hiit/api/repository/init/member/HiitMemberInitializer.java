package com.hiit.api.repository.init.member;

import com.hiit.api.repository.dao.bussiness.HiitMemberRepository;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class HiitMemberInitializer {

	@Autowired HiitMemberRepository repository;

	private HiitMemberEntity data;

	public void initialize() {
		repository.deleteAllInBatch();
		this.setData();
	}

	private HiitMemberEntity setData() {
		HiitMemberEntity source = FakeHiitMember.create();
		this.data = repository.save(source);
		return this.data;
	}

	public HiitMemberEntity getData() {
		return data;
	}
}
