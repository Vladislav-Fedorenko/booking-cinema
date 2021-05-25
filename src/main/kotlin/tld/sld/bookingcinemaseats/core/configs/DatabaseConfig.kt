package tld.sld.bookingcinemaseats.core.configs

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
@Profile(value = ["!test"])
class DatabaseConfig {
    @Bean
    @Primary
    @Qualifier("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    @Qualifier("jdbcTemplate")
    fun jdbcTemplate(@Qualifier("dataSource") dataSource: DataSource): JdbcTemplate {
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