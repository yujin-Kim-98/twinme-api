package com.api.twinme.common.config.database

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["com.api.twinme"],
    entityManagerFactoryRef = "twinmeEntityManager",
    transactionManagerRef = "twinmeTransactionManager"
)
class TwinmeDatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "twinme.datasource")
    fun twinmeDataSourceProperties(): DataSourceProperties = DataSourceProperties()

    @Bean
    @ConfigurationProperties(prefix = "twinme.datasource.hikari")
    fun twinmeHikariDataSource(
        @Qualifier("twinmeDataSourceProperties") property: DataSourceProperties
    ): HikariDataSource = property.initializeDataSourceBuilder().type(HikariDataSource::class.java)
        .build()

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "twinme.jpa")
    fun twinmeJpaProperties(): JpaProperties = JpaProperties()

    @Bean
    fun twinmeEntityManager(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("twinmeHikariDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean = builder.dataSource(dataSource)
        .packages("com.api.twinme")
        .build()

    @Bean
    fun twinmeTransactionManager(
        entityManager: EntityManagerFactory
    ): PlatformTransactionManager = JpaTransactionManager(entityManager)

}

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class JpaAuditingConfig {
    @Bean
    fun auditorProvider() = AuditorAware { Optional.of("twin-me") }
}