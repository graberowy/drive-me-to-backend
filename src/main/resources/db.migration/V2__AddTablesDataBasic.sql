INSERT INTO app_users (user_login, user_password) VALUES ('Pawel123', '12345');

INSERT INTO app_user_roles (app_user_id, roles) VALUES (1, 'CUSTOMER');

INSERT INTO passengers (name, mobile) VALUES ('Adam Kowalski', '111222333');

INSERT INTO cars (price_per_km, currency, make, status) VALUES (1.7, 'PLN', 'BMW', 'READY_TO_GO');
INSERT INTO cars (price_per_km, currency, make, status) VALUES (2.7, 'PLN', 'MERCEDES', 'READY_TO_GO');
INSERT INTO cars (price_per_km, currency, make, status) VALUES (2.1, 'PLN', 'SUBARU', 'READY_TO_GO');

INSERT INTO drivers (name, car_id) VALUES ('Paweł Nowak', 1);
INSERT INTO drivers (name, car_id) VALUES ('Sebastian Zieliński', 2);
INSERT INTO drivers (name, car_id) VALUES ('Kamil Kowalczyk', 3);

INSERT INTO driver_languages (driver_id, languages) VALUES (1, 'ENGLISH');
INSERT INTO driver_languages (driver_id, languages) VALUES (1, 'POLISH');
INSERT INTO driver_languages (driver_id, languages) VALUES (1, 'RUSSIAN');

INSERT INTO driver_languages (driver_id, languages) VALUES (2, 'ENGLISH');
INSERT INTO driver_languages (driver_id, languages) VALUES (2, 'POLISH');
INSERT INTO driver_languages (driver_id, languages) VALUES (2, 'GERMAN');

INSERT INTO driver_languages (driver_id, languages) VALUES (3, 'ENGLISH');
INSERT INTO driver_languages (driver_id, languages) VALUES (3, 'POLISH');
INSERT INTO driver_languages (driver_id, languages) VALUES (3, 'GERMAN');
INSERT INTO driver_languages (driver_id, languages) VALUES (3, 'RUSSIAN');