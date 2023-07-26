package com.hiit.api.infra.flyway.config;

import static com.hiit.api.infra.flyway.FlywayConfig.BEAN_NAME_PREFIX;
import static com.hiit.api.infra.flyway.FlywayConfig.PROPERTY_PREFIX;

import com.hiit.api.repository.config.EntityJpaDataSourceConfig;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/** Entity 관련 schema Flyway 설정 클래스 */
@Configuration
public class EntityFlywayConfig {

	// base property prefix for flyway
	public static final String BASE_PROPERTY_PREFIX = PROPERTY_PREFIX;

	// bean name for flyway configuration
	private static final String FLYWAY = BEAN_NAME_PREFIX;
	private static final String FLYWAY_PROPERTIES = BEAN_NAME_PREFIX + "Properties";
	private static final String FLYWAY_MIGRATION_INITIALIZER =
			BEAN_NAME_PREFIX + "MigrationInitializer";
	private static final String FLYWAY_VALIDATE_INITIALIZER =
			BEAN_NAME_PREFIX + "ValidateInitializer";
	private static final String FLYWAY_CONFIGURATION = BEAN_NAME_PREFIX + "Configuration";

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
	 * Flyway Validate Bean을 생성한다.
	 *
	 * @param flyway Flyway 객체
	 * @return Flyway Validate을 수행하는 FlywayMigrationInitializer 객체
	 */
	@Profile({"!local"})
	@Bean(name = FLYWAY_VALIDATE_INITIALIZER)
	public FlywayMigrationInitializer flywayValidateInitializer(
			@Qualifier(value = FLYWAY) Flyway flyway) {
		return new FlywayMigrationInitializer(flyway, Flyway::validate);
	}

	/**
	 * Flyway Migration Bean을 생성한다.
	 *
	 * @param flyway Flyway 객체
	 * @return Flyway Migration을 수행하는 FlywayMigrationInitializer 객체
	 */
	@Profile({"!local"})
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
	@ConfigurationProperties(prefix = BASE_PROPERTY_PREFIX)
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
			@Qualifier(value = EntityJpaDataSourceConfig.DATASOURCE_NAME) DataSource dataSource) {

		FluentConfiguration configuration = new FluentConfiguration();
		configuration.dataSource(dataSource);
		flywayProperties.getLocations().forEach(configuration::locations);
		return configuration;
	}
}
