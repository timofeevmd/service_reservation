package com.service.database;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class DatabaseCleaner {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PreDestroy
    public void cleanDatabase() {
        logger.info("Cleaning up database before shutdown...");
        try {
            jdbcTemplate.execute("DROP SCHEMA public CASCADE;");
            jdbcTemplate.execute("CREATE SCHEMA public;");
            jdbcTemplate.execute("GRANT ALL ON SCHEMA public TO test_user;");
            jdbcTemplate.execute("GRANT CREATE ON SCHEMA public TO test_user;");
            logger.info("Database cleaned and reset successfully.");
        } catch (Exception e) {
            System.err.println("Error while cleaning the database: " + e.getMessage());
        }
    }
}