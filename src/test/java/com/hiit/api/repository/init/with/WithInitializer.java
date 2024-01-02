package com.hiit.api.repository.init.with;

import com.hiit.api.repository.dao.bussiness.WithRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class WithInitializer {

	@Autowired WithRepository repository;

	private WithEntity data;

	public void initialize(InItEntity inIt, HiitMemberEntity hiitMember) {
		repository.deleteAll();
		this.setData(inIt, hiitMember);
	}

	private WithEntity setData(InItEntity inIt, HiitMemberEntity hiitMember) {
		WithEntity source = FakeWith.create(inIt, hiitMember);
		this.data = repository.save(source);
		return this.data;
	}

	public WithEntity addData(InItEntity inIt, HiitMemberEntity hiitMember) {
		WithEntity source = FakeWith.create(inIt, hiitMember);
		return repository.save(source);
	}

	public WithEntity getData() {
		return data;
	}
}
