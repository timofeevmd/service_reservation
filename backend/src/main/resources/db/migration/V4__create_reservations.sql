CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,                      -- Уникальный идентификатор аренды
    user_id INT NOT NULL,                       -- Ссылка на пользователя
    type VARCHAR(50) NOT NULL,                  -- Тип аренды (CAR, ROOM)
    item_id INT NOT NULL,                       -- ID связанного элемента (машина или номер)
    start_date DATE NOT NULL,                   -- Дата начала аренды
    end_date DATE NOT NULL,                     -- Дата завершения аренды
    duration INT NOT NULL DEFAULT 0,            -- Продолжительность бронирования (количество дней)
    status VARCHAR(50) DEFAULT 'Active',        -- Статус аренды (Active, Completed)
    created_at TIMESTAMP DEFAULT NOW(),         -- Дата создания записи
    FOREIGN KEY (user_id) REFERENCES users (id) -- Связь с таблицей пользователей
);