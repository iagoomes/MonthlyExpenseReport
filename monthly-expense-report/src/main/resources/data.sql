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

INSERT INTO invoice_request (customer_id, status, submitted_at, analysis_results, amount, due_date, token_count)
VALUES (1, 'Pending', '2024-08-01 10:30:00', NULL, 500.00, '2024-08-31', 100),
       (2, 'In Review', '2024-08-02 11:00:00', NULL, 750.00, '2024-09-15', 150),
       (3, 'Completed', '2024-08-03 12:45:00', 'Potential savings identified', 300.00, '2024-08-30', 75),
       (4, 'Pending', '2024-08-04 09:20:00', NULL, 200.00, '2024-09-01', 50),
       (5, 'Completed', '2024-08-05 14:15:00', 'Analysis complete', 1000.00, '2024-09-10', 250);

-- Inserindo dados na tabela payment
INSERT INTO payment (invoice_request_id, amount, payment_date, payment_method, status, token_count)
VALUES (1, 100.00, '2024-08-05 15:00:00', 'Credit Card', 'Completed', 20),
       (2, 150.00, '2024-08-06 10:00:00', 'Bank Transfer', 'Completed', 30),
       (3, 75.00, '2024-08-07 08:30:00', 'PayPal', 'Completed', 15),
       (5, 250.00, '2024-08-08 16:00:00', 'Debit Card', 'Completed', 50),
       (1, 50.00, '2024-08-09 12:00:00', 'Credit Card', 'Pending', 10);