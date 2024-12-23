CREATE TABLE IF NOT EXISTS person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    personal_id VARCHAR(255) NOT NULL,
    CONSTRAINT unique_personal_number UNIQUE (personal_id)
);

CREATE TABLE IF NOT EXISTS customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    person_id BIGINT,
    CONSTRAINT fk_customer_person FOREIGN KEY (person_id) REFERENCES person(id)
);

CREATE TABLE IF NOT EXISTS employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    person_id BIGINT,
    CONSTRAINT fk_employee_person FOREIGN KEY (person_id) REFERENCES person(id)
);
