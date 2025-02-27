CREATE TABLE logs (
    id SERIAL PRIMARY KEY,                                      -- Уникальный идентификатор лога
    event_type VARCHAR(50) NOT NULL,                            -- Тип события
    user_id INT,                                                -- ID пользователя (если применимо)
    reservation_id INT,                                         -- ID бронирования (если применимо)
    message TEXT,                                               -- Дополнительная информация
    created_at TIMESTAMP DEFAULT NOW(),                         -- Время события
    FOREIGN KEY (user_id) REFERENCES users (id),                -- Связь с таблицей пользователей
    FOREIGN KEY (reservation_id) REFERENCES reservations (id)   -- Связь с таблицей бронирований
);