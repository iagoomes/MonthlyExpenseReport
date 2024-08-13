-- Inserindo dados na tabela customer
INSERT INTO customer (first_name, last_name, user_name, email, password, income, favorite_bank, created_at, updated_at)
VALUES
('John', 'Doe', 'johndoe123', 'johndoe@example.com', 'securepassword123', '75000', 'Bank of Example', NOW(), NOW()),
('Jane', 'Smith', 'janesmith456', 'janesmith@example.com', 'securepassword456', '85000', 'Another Bank', NOW(), NOW()),
('Alice', 'Johnson', 'alicejohnson789', 'alicejohnson@example.com', 'securepassword789', '95000', 'Example Bank', NOW(), NOW()),
('Bob', 'Williams', 'bobwilliams101', 'bobwilliams@example.com', 'securepassword101', '70000', 'Sample Bank', NOW(), NOW()),
('Charlie', 'Brown', 'charliebrown202', 'charliebrown@example.com', 'securepassword202', '80000', 'Test Bank', NOW(), NOW());

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