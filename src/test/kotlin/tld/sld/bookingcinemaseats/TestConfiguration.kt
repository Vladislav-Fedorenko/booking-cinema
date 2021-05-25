package tld.sld.bookingcinemaseats

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Profile("test")
@TestConfiguration
class TestConfiguration {

    @Bean
    @Qualifier("databaseContainer")
    fun testDatabaseContainer(): TestPostgreSQLContainer {
        return TestPostgreSQLContainer(POSTGRES_IMAGE_NAME)
    }

    @Bean
    @Primary
    @Qualifier("dataSource")
    fun testDataSource(@Qualifier("databaseContainer") container: TestPostgreSQLContainer): DataSource {
        container.start()
        return DataSourceBuilder.create()
            .driverClassName(container.driverClassName)
            .url(container.jdbcUrl)
            .username(container.username)
            .password(container.password)
            .build()
    }

    @Bean
    @Qualifier("jdbcTemplate")
    fun testJdbcTemplate(@Qualifier("dataSource") dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }

    @Bean
    fun flyway(dataSource: DataSource): Flyway {
        val flyway = Flyway.configure().dataSource(dataSource).load()
        flyway.repair()
        flyway.migrate()
        return flyway
    }
}