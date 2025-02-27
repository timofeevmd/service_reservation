CREATE TABLE users (
    id SERIAL PRIMARY KEY,                -- Уникальный идентификатор
    username VARCHAR(50) UNIQUE NOT NULL, -- Уникальное имя пользователя
    password VARCHAR(255) NOT NULL,       -- Хэшированный пароль
    role VARCHAR(20) DEFAULT 'USER',      -- Роль (USER, ADMIN)
    created_at TIMESTAMP DEFAULT NOW()    -- Дата создания записи
);