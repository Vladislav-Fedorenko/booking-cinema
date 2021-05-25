package tld.sld.bookingcinemaseats

import org.testcontainers.containers.PostgreSQLContainer

class TestPostgreSQLContainer(imageName: String) : PostgreSQLContainer<TestPostgreSQLContainer>(imageName)

const val POSTGRES_IMAGE_NAME = "postgres:13-alpine"
const val POSTGRES_SQL_INIT_SCRIPT = "jdbc/postgres.sql"