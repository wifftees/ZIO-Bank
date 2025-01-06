CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    money_amount DOUBLE PRECISION NOT NULL
);

INSERT INTO person (username, password, money_amount) VALUES
('johndoe', 'password123', 1000.50),
('janedoe', 'securePass456', 1500.75),
('alicejones', 'alicePassword789', 2000.00),
('bobsmith', 'bobPassword321', 1200.20);