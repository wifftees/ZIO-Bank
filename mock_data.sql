CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    money_amount DOUBLE PRECISION NOT NULL
);

CREATE TABLE transaction (
    id SERIAL PRIMARY KEY,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES person(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES person(id) ON DELETE CASCADE
);

INSERT INTO person (username, password, money_amount) VALUES
('johndoe', 'password123', 1000.50),
('janedoe', 'securePass456', 1500.75),
('alicejones', 'alicePassword789', 2000.00),
('bobsmith', 'bobPassword321', 1200.20);

INSERT INTO transaction (sender_id, receiver_id, amount) VALUES
(1, 2, 200.00),  -- johndoe sends 200.00 to janedoe
(2, 3, 350.00),  -- janedoe sends 350.00 to alicejones
(3, 4, 150.00),  -- alicejones sends 150.00 to bobsmith
(4, 1, 100.00);  -- bobsmith sends 100.00 to johndoe