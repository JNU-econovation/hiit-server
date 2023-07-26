package com.hiit.api.repository.config;

import com.hiit.api.repository.RepositoryConfig;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA Datasource 설정 클래스<br>
 * RepositoryConfig에서 자동구성 설정을 제외한 경우에 사용한다.
 *
 * @see RepositoryConfig
 */
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = RepositoryConfig.BASE_PACKAGE,
		transactionManagerRef = EntityJpaDataSourceConfig.TRANSACTION_MANAGER_NAME,
		entityManagerFactoryRef = EntityJpaDataSourceConfig.ENTITY_MANAGER_FACTORY_NAME)
public class EntityJpaDataSourceConfig {

	// base property prefix for jpa datasource
	private static final String BASE_PROPERTY_PREFIX =
			RepositoryConfig.PROPERTY_PREFIX + ".mysql.entity";

	// bean name for jpa datasource configuration
	public static final String ENTITY_BEAN_NAME_PREFIX = RepositoryConfig.BEAN_NAME_PREFIX + "Entity";
	public static final String ENTITY_MANAGER_FACTORY_NAME =
			ENTITY_BEAN_NAME_PREFIX + "ManagerFactory";
	public static final String TRANSACTION_MANAGER_NAME =
			ENTITY_BEAN_NAME_PREFIX + "TransactionManager";
	public static final String DATASOURCE_NAME = ENTITY_BEAN_NAME_PREFIX + "DataSource";
	private static final String JPA_PROPERTIES_NAME = ENTITY_BEAN_NAME_PREFIX + "JpaProperties";
	private static final String HIBERNATE_PROPERTIES_NAME =
			ENTITY_BEAN_NAME_PREFIX + "HibernateProperties";
	private static final String JPA_VENDOR_ADAPTER_NAME =
			ENTITY_BEAN_NAME_PREFIX + "JpaVendorAdapter";
	private static final String PERSIST_UNIT = ENTITY_BEAN_NAME_PREFIX + "PersistenceUnit";
	private static final String ENTITY_MANAGER_FACTORY_BUILDER_NAME =
			ENTITY_BEAN_NAME_PREFIX + "ManagerFactoryBuilder";

	/**
	 * DataSource Bean을 생성한다.
	 *
	 * @return DataSource 설정 요소를 읽어오는 DataSource 객체
	 */
	@Bean(name = DATASOURCE_NAME)
	@ConfigurationProperties(prefix = BASE_PROPERTY_PREFIX + ".datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * JpaProperties Bean을 생성한다.
	 *
	 * @return Jpa 설정 요소를 읽어오는 JpaProperties 객체
	 */
	@Bean(name = JPA_PROPERTIES_NAME)
	@ConfigurationProperties(prefix = BASE_PROPERTY_PREFIX + ".jpa")
	public JpaProperties jpaProperties() {
		return new JpaProperties();
	}

	/**
	 * HibernateProperties Bean을 생성한다.
	 *
	 * @return Hibernate 설정 요소를 읽어오는 HibernateProperties 객체
	 */
	@Bean(name = HIBERNATE_PROPERTIES_NAME)
	@ConfigurationProperties(prefix = BASE_PROPERTY_PREFIX + ".jpa.hibernate")
	public HibernateProperties hibernateProperties() {
		return new HibernateProperties();
	}

	/**
	 * JpaVendorAdapter Bean을 생성한다.
	 *
	 * @return JpaVendorAdapter 객체
	 */
	@Bean(name = JPA_VENDOR_ADAPTER_NAME)
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	/**
	 * EntityManagerFactoryBuilder Bean을 생성한다.
	 *
	 * @param jpaVendorAdapter JpaVendorAdapter 객체
	 * @param jpaProperties JpaProperties 객체
	 * @param persistenceUnitManager PersistenceUnitManager 객체
	 * @return EntityManagerFactoryBuilder 객체
	 */
	@Bean(name = ENTITY_MANAGER_FACTORY_BUILDER_NAME)
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
			@Qualifier(value = JPA_VENDOR_ADAPTER_NAME) JpaVendorAdapter jpaVendorAdapter,
			@Qualifier(value = JPA_PROPERTIES_NAME) JpaProperties jpaProperties,
			ObjectProvider<PersistenceUnitManager> persistenceUnitManager) {

		Map<String, String> jpaPropertyMap = jpaProperties.getProperties();
		return new EntityManagerFactoryBuilder(
				jpaVendorAdapter, jpaPropertyMap, persistenceUnitManager.getIfAvailable());
	}

	/**
	 * LocalContainerEntityManagerFactory Bean을 생성한다.
	 *
	 * @param dataSource Datasource 객체
	 * @param builder EntityManagerFactoryBuilder 객체
	 * @return EntityManagerFactory 객체
	 */
	@Bean(name = ENTITY_MANAGER_FACTORY_NAME)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			@Qualifier(value = DATASOURCE_NAME) DataSource dataSource,
			@Qualifier(value = ENTITY_MANAGER_FACTORY_BUILDER_NAME) EntityManagerFactoryBuilder builder) {
		Map<String, String> jpaPropertyMap = jpaProperties().getProperties();
		Map<String, Object> hibernatePropertyMap =
				hibernateProperties().determineHibernateProperties(jpaPropertyMap, new HibernateSettings());
		return builder
				.dataSource(dataSource)
				.properties(hibernatePropertyMap)
				.persistenceUnit(PERSIST_UNIT)
				.packages(RepositoryConfig.BASE_PACKAGE)
				.build();
	}

	/**
	 * PlatformTransactionManager Bean을 생성한다.
	 *
	 * @param emf EntityManagerFactory 객체
	 * @return PlatformTransactionManager 객체
	 */
	@Bean(name = TRANSACTION_MANAGER_NAME)
	public PlatformTransactionManager transactionManager(
			@Qualifier(ENTITY_MANAGER_FACTORY_NAME) EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
}
