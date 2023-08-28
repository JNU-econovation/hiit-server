package com.hiit.api.infra.flyway.config;

import static com.hiit.api.infra.flyway.FlywayConfig.BEAN_NAME_PREFIX;
import static com.hiit.api.infra.flyway.FlywayConfig.PROPERTY_PREFIX;

import com.hiit.api.batch.config.BatchDataSourceConfig;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConditionalOnClass(value = Flyway.class)
@ConditionalOnBean(value = javax.sql.DataSource.class)
public class BatchFlywayConfig {

	// base property prefix for flyway
	public static final String BASE_PROPERTY_PREFIX = PROPERTY_PREFIX;

	// bean name for flyway configuration
	private static final String FLYWAY = BEAN_NAME_PREFIX + "Batch";
	private static final String FLYWAY_PROPERTIES = BEAN_NAME_PREFIX + "BatchProperties";
	private static final String FLYWAY_MIGRATION_INITIALIZER =
			BEAN_NAME_PREFIX + "BatchMigrationInitializer";
	private static final String FLYWAY_CONFIGURATION = BEAN_NAME_PREFIX + "BatchConfiguration";

	/**
	 * Flyway Bean을 생성한다.
	 *
	 * @param configuration Flyway Configuration 객체
	 * @return Flyway 객체
	 */
	@Bean(name = FLYWAY)
	public Flyway flyway(
			@Qualifier(value = FLYWAY_CONFIGURATION)
					org.flywaydb.core.api.configuration.Configuration configuration) {
		return new Flyway(configuration);
	}

	/**
	 * Flyway Migration Bean을 생성한다.
	 *
	 * @param flyway Flyway 객체
	 * @return Flyway Migration을 수행하는 FlywayMigrationInitializer 객체
	 */
	@Profile({"!local && !test"})
	@Bean(name = FLYWAY_MIGRATION_INITIALIZER)
	public FlywayMigrationInitializer flywayMigrationInitializer(
			@Qualifier(value = FLYWAY) Flyway flyway) {
		return new FlywayMigrationInitializer(flyway, Flyway::migrate);
	}

	/**
	 * Flyway Properties Bean을 생성한다.
	 *
	 * @return Flyway 설정 요소를 읽어오는 FlywayProperties 객체
	 */
	@Bean(name = FLYWAY_PROPERTIES)
	@ConfigurationProperties(prefix = BASE_PROPERTY_PREFIX + ".batch")
	public FlywayProperties flywayProperties() {
		return new FlywayProperties();
	}

	/**
	 * Flyway Configuration Bean을 생성한다.
	 *
	 * @param flywayProperties FlywayProperties 객체
	 * @param dataSource Entity 설정 DataSource 객체
	 * @return FlywayConfiguration 객체
	 */
	@Bean(name = FLYWAY_CONFIGURATION)
	public org.flywaydb.core.api.configuration.Configuration configuration(
			@Qualifier(value = FLYWAY_PROPERTIES) FlywayProperties flywayProperties,
			@Qualifier(value = BatchDataSourceConfig.DATASOURCE_NAME) DataSource dataSource) {

		FluentConfiguration configuration = new FluentConfiguration();
		configuration.dataSource(dataSource);
		flywayProperties.getLocations().forEach(configuration::locations);
		return configuration;
	}
}
