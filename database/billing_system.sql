-- Billing System database script (import via phpMyAdmin)
CREATE DATABASE IF NOT EXISTS billing_system;
USE billing_system;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

-- Default login: username = admin, password = admin123 (stored as SHA-256 hash)
INSERT IGNORE INTO users (username, password) VALUES ('admin', SHA2('admin123', 256));
