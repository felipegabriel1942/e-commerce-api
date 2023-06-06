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

CREATE TABLE sale_product (
    id SERIAL PRIMARY KEY,
    sale_id bigint NOT NULL,
    product_id bigint NOT NULL,
    product_price NUMERIC(38,2),
    CONSTRAINT fk_sale FOREIGN KEY (sale_id) REFERENCES sale (id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id)
);