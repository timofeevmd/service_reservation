CREATE TABLE cars (
    id SERIAL PRIMARY KEY,                           -- Уникальный идентификатор машины
    model VARCHAR(100) NOT NULL,                     -- Модель машины
    registration_number VARCHAR(50) UNIQUE NOT NULL, -- Уникальный регистрационный номер
    available BOOLEAN DEFAULT TRUE,                  -- Доступность (по умолчанию доступна)
    created_at TIMESTAMP DEFAULT NOW()               -- Дата добавления записи
);