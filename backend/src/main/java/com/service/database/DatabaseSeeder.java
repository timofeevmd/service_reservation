package com.service.database;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


@Component
public class DatabaseSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Random random = new Random();

    public DatabaseSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostConstruct
    public void seedDatabase() {
        seedUsers();
        seedCars();
        seedHotelRooms();
        seedCarReservations();
        seedRoomReservations();
        logger.info("Sample data inserted successfully.");
    }

    private void seedUsers() {
        try (FileWriter writer = new FileWriter("users_passwords.csv")) {
            writer.append("username,password\n");
            for (int i = 1; i <= 100; i++) {
                String username = "user_" + i;
                String rawPassword = "password_" + i;
                String hashedPassword = passwordEncoder.encode(rawPassword);

                jdbcTemplate.update("INSERT INTO users (username, password, role, created_at) VALUES (?, ?, ?, NOW()) ON CONFLICT (username) DO NOTHING",
                        username, hashedPassword, "USER");

                writer.append(username).append(",").append(rawPassword).append("\n");
            }
            logger.info("Passwords saved to users_passwords.csv");
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("100 users inserted with hashed passwords.");
    }

    private void seedCars() {
        for (int i = 1; i <= 50; i++) {
            String model = "Model_" + i;
            String registrationNumber = "REG" + i;
            boolean available = random.nextBoolean();

            jdbcTemplate.update("INSERT INTO cars (model, registration_number, available, created_at) VALUES (?, ?, ?, NOW()) ON CONFLICT (registration_number) DO NOTHING",
                    model, registrationNumber, available);
        }
        logger.info("50 cars inserted.");
    }

    private void seedHotelRooms() {
        for (int i = 1; i <= 80; i++) {
            int capacity = random.nextInt(5) + 1;

            jdbcTemplate.update("INSERT INTO hotel_rooms (room_number, capacity, available, created_at) VALUES (?, ?, ?, NOW()) ON CONFLICT (room_number) DO NOTHING",
                    i, capacity, true);
        }
        logger.info("80 hotel rooms inserted.");
    }

    private void seedCarReservations() {
        for (int i = 1; i <= 500; i++) {
            int userId = getRandomUserId();
            int carId = random.nextInt(50) + 1;
            int startDateOffset = random.nextInt(30) * -1;
            int endDateOffset = random.nextInt(10);
            String status = random.nextBoolean() ? "Active" : "Completed";

            jdbcTemplate.update("INSERT INTO reservations (user_id, type, item_id, start_date, end_date, status, duration, created_at) VALUES (?, ?, ?, CURRENT_DATE + ?, CURRENT_DATE + ?, ?, ?, NOW()) ON CONFLICT DO NOTHING",
                    userId, "CAR", carId, startDateOffset, endDateOffset, status, endDateOffset - startDateOffset);
        }
        logger.info("500 car reservations inserted.");
    }

    private void seedRoomReservations() {
        for (int i = 1; i <= 500; i++) {
            int userId = getRandomUserId();
            int roomId = random.nextInt(80) + 1;
            int startDateOffset = random.nextInt(30) * -1;
            int endDateOffset = random.nextInt(10);
            String status = random.nextBoolean() ? "Active" : "Completed";

            jdbcTemplate.update("INSERT INTO reservations (user_id, type, item_id, start_date, end_date, status, duration, created_at) VALUES (?, ?, ?, CURRENT_DATE + ?, CURRENT_DATE + ?, ?, ?, NOW()) ON CONFLICT DO NOTHING",
                    userId, "ROOM", roomId, startDateOffset, endDateOffset, status, endDateOffset - startDateOffset);
        }
        logger.info("500 room reservations inserted.");
    }

    private int getRandomUserId() {
        Integer userId = jdbcTemplate.queryForObject("SELECT id FROM users ORDER BY random() LIMIT 1", Integer.class);
        return userId != null ? userId : 1;
    }
}
