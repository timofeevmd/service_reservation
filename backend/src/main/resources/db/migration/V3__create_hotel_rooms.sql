CREATE TABLE hotel_rooms (
    id SERIAL PRIMARY KEY,             -- Уникальный идентификатор номера
    room_number INT UNIQUE NOT NULL,   -- Уникальный номер комнаты
    capacity INT NOT NULL,             -- Вместимость номера
    available BOOLEAN DEFAULT TRUE,    -- Доступность (по умолчанию доступен)
    created_at TIMESTAMP DEFAULT NOW() -- Дата добавления записи
);