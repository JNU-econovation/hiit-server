package com.hiit.api.repository;

import com.hiit.api.infra.InfraConfig;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@DataJpaTest(
		excludeAutoConfiguration = {
			DataSourceAutoConfiguration.class,
			DataSourceTransactionManagerAutoConfiguration.class,
			HibernateJpaAutoConfiguration.class,
		})
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = {RepositoryConfig.class, InfraConfig.class})
public abstract class AbstractRepositoryTest {}
