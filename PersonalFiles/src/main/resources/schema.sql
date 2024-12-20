CREATE TABLE IF NOT EXISTS Person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    personal_id VARCHAR(255) NOT NULL
);
ALTER TABLE person ADD CONSTRAINT unique_personal_number UNIQUE (personal_id);