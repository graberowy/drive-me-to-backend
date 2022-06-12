CREATE TABLE IF NOT EXISTS app_users (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_login VARCHAR(20) NOT NULL,
    user_password VARCHAR(20) NOT NULL,
    UNIQUE(user_login)
);
CREATE TABLE IF NOT EXISTS app_user_roles (
    app_user_id INTEGER,
    roles ENUM('CUSTOMER', 'EMPLOYEE', 'COMMERCIAL_USER'),
    FOREIGN KEY (app_user_id) REFERENCES app_users(id),
    CONSTRAINT fk_app_user_id_roles PRIMARY KEY(app_user_id, roles)
);
CREATE TABLE IF NOT EXISTS passengers (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    mobile VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS cars (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    current_location VARCHAR(255),
    price_per_km DECIMAL(5,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    make ENUM('MERCEDES', 'BMW', 'SUBARU'),
    status ENUM('READY_TO_GO', 'DRIVING')
);
CREATE TABLE IF NOT EXISTS drivers (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    car_id INTEGER,
    FOREIGN KEY (car_id) REFERENCES cars(id)
);
CREATE TABLE IF NOT EXISTS driver_languages (
    driver_id INTEGER,
    languages ENUM('ENGLISH', 'POLISH', 'GERMAN', 'RUSSIAN'),
    FOREIGN KEY (driver_id) REFERENCES drivers(id),
    CONSTRAINT fk_driver_id_languages PRIMARY KEY(driver_id, languages)
);
CREATE TABLE IF NOT EXISTS trips (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    start_address VARCHAR(100) NOT NULL,
    finish_address VARCHAR(100) NOT NULL,
    full_start_address VARCHAR(255),
    full_finish_address VARCHAR(255),
    paid BIT,
    price DOUBLE(5,2),
    distance DOUBLE(5,1),
    rating TINYINT,
    prefer_language ENUM('ENGLISH', 'POLISH', 'GERMAN', 'RUSSIAN'),
    prefer_car_make ENUM('MERCEDES', 'BMW', 'SUBARU'),
    prefer_route_type ENUM('FAST', 'SHORT'),
    passenger_id INTEGER NOT NULL,
    driver_id INTEGER,
    CONSTRAINT fk_passenger_trips_id
    FOREIGN KEY (passenger_id)
    REFERENCES passengers(id),
    CONSTRAINT fk_driver_trips_id
    FOREIGN KEY (driver_id)
    REFERENCES drivers(id)
);
