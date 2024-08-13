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

INSERT INTO invoice_request (customer_id, status, amount, token_count)
VALUES
    (1, 'Completed', 1500.00, 150),
    (2, 'In Review', 2500.00, 250),
    (3, 'Payment Pending', 3500.00, 350);

-- Inserindo dados na tabela payment
INSERT INTO payment (invoice_request_id, payment_amount, payment_method, card_number, card_holder_name, card_expiry_date, card_cvv, token_count, payment_status)
VALUES
    (1, 1500.00, 'Credit Card', '1234567812345678', 'Alice', '2025-12-31', '123', 150, 'Completed'),
    (2, 2500.00, 'Debit Card', '2345678923456789', 'Bob', '2024-11-30', '456', 250, 'Processing'),
    (3, 3500.00, 'Credit Card', '3456789034567890', 'Charlie', '2026-10-31', '789', 350, 'Failed');