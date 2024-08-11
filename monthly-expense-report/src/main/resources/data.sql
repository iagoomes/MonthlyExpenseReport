-- Inserindo dados na tabela customer
INSERT INTO customer (first_name, last_name, email, phone_number, address, city, state, postal_code, country)
VALUES ('John', 'Doe', 'johndoe@example.com', '123-456-7890', '123 Elm Street', 'Springfield', 'IL', '62701', 'USA'),
       ('Jane', 'Smith', 'janesmith@example.com', '234-567-8901', '456 Oak Avenue', 'Metropolis', 'NY', '10001', 'USA'),
       ('Alice', 'Johnson', 'alicejohnson@example.com', '345-678-9012', '789 Pine Road', 'Gotham', 'NJ', '07001',
        'USA'),
       ('Bob', 'Williams', 'bobwilliams@example.com', '456-789-0123', '101 Maple Lane', 'Star City', 'CA', '90001',
        'USA'),
       ('Charlie', 'Brown', 'charliebrown@example.com', '567-890-1234', '202 Birch Street', 'Smallville', 'TX', '75001',
        'USA');

-- Inserindo dados na tabela payment
INSERT INTO payment (customer_id, amount, payment_date, payment_method, status)
VALUES (1, 100.00, '2024-08-01 10:30:00', 'Credit Card', 'Completed'),
       (2, 150.50, '2024-08-02 14:45:00', 'Bank Transfer', 'Completed'),
       (3, 200.75, '2024-08-03 09:20:00', 'PayPal', 'Pending'),
       (1, 50.00, '2024-08-04 12:15:00', 'Credit Card', 'Completed'),
       (4, 75.25, '2024-08-05 11:00:00', 'Debit Card', 'Completed'),
       (5, 300.00, '2024-08-06 16:30:00', 'Bank Transfer', 'Completed'),
       (2, 120.00, '2024-08-07 13:45:00', 'Credit Card', 'Failed');
