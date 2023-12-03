package com.hiit.api.repository.init.it;

import com.hiit.api.repository.dao.bussiness.RegisteredItRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class RegisteredItInitializer {

	@Autowired RegisteredItRepository repository;

	private RegisteredItEntity data;

	public void initialize() {
		repository.deleteAll();
		this.setData();
	}

	public void initialize(InItEntity inIt) {
		repository.deleteAll();
		this.setData();
	}

	private void setData() {
		RegisteredItEntity source = FakeRegisteredIt.create();
		repository.save(source);
		this.data = source;
	}

	public RegisteredItEntity getData() {
		return data;
	}
}
