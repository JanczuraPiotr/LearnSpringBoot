INSERT INTO person (id, name, surname, personal_id) VALUES (1, '123', '123', '12345');
INSERT INTO person (id, name, surname, personal_id) VALUES (2, '1234', '1234', '23456');
INSERT INTO person (id, name, surname, personal_id) VALUES (3, '12345', '12345', '34567');
INSERT INTO person (id, name, surname, personal_id) VALUES (4, '123456', '123456', '445678');
INSERT INTO person (id, name, surname, personal_id) VALUES (5, '1234567', '1234567', '5456789');

INSERT INTO customer(id, person_id) VALUES (1, 2);
INSERT INTO customer(id, person_id) VALUES (2, 3);
INSERT INTO employee(id, person_id) VALUES (1, 3);
INSERT INTO employee(id, person_id) VALUES (2, 4);