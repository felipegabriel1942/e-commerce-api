CREATE TABLE _user(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255)
);

INSERT INTO _user(email, password, role)
VALUES ('admin@email.com', '$2a$10$6W9lFh22y.yDfVIh8ZASa.iyqZNbJpuP1VM3ofQWHUFcyxG0d6TJq', 'ADMIN');

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    price NUMERIC(38,2),
    status VARCHAR(255)
);

CREATE TABLE sale (
    id SERIAL PRIMARY KEY,
    date DATE,
    status VARCHAR(255),
    _user bigint
);

CREATE TABLE sale_products (
    sale_id bigint NOT NULL,
    products_id bigint NOT NULL
);