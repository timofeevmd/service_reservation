package com.service.database;

import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class DatabaseCleaner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PreDestroy
    public void cleanDatabase() {
        System.out.println("Cleaning up database before shutdown...");
        try {
            jdbcTemplate.execute("DROP SCHEMA public CASCADE;");
            jdbcTemplate.execute("CREATE SCHEMA public;");
            jdbcTemplate.execute("GRANT ALL ON SCHEMA public TO test_user;");
            jdbcTemplate.execute("GRANT CREATE ON SCHEMA public TO test_user;");
            System.out.println("Database cleaned and reset successfully.");
        } catch (Exception e) {
            System.err.println("Error while cleaning the database: " + e.getMessage());
        }
    }
}