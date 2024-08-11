-- Criando tabela customer
CREATE TABLE customer
(
    id           INT AUTO_INCREMENT PRIMARY KEY,                                 -- Identificador único do cliente
    first_name   VARCHAR(50)  NOT NULL,                                          -- Nome do cliente
    last_name    VARCHAR(50)  NOT NULL,                                          -- Sobrenome do cliente
    email        VARCHAR(100) NOT NULL UNIQUE,                                   -- E-mail do cliente, único
    phone_number VARCHAR(20),                                                    -- Número de telefone do cliente
    address      VARCHAR(255),                                                   -- Endereço completo do cliente
    city         VARCHAR(50),                                                    -- Cidade do cliente
    state        VARCHAR(50),                                                    -- Estado do cliente
    postal_code  VARCHAR(20),                                                    -- Código postal do cliente
    country      VARCHAR(50),                                                    -- País do cliente
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                            -- Data e hora de criação
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Data e hora da última atualização
);

-- Criando tabela payment
CREATE TABLE payment
(
    id             INT AUTO_INCREMENT PRIMARY KEY,                        -- Identificador único do pagamento
    customer_id    INT            NOT NULL,                               -- Identificador do cliente (chave estrangeira)
    amount         DECIMAL(10, 2) NOT NULL,                               -- Valor do pagamento
    payment_date   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,                 -- Data e hora do pagamento
    payment_method VARCHAR(50),                                           -- Método de pagamento (ex: cartão de crédito, transferência)
    status         VARCHAR(20) DEFAULT 'Pending',-- Status do pagamento (ex: Pending, Completed, Failed)
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE, -- Relacionamento com a tabela customer
    CONSTRAINT chk_amount_positive CHECK (amount > 0)                     -- Validação para garantir que o valor do pagamento seja positivo
);

-- Criando índices para melhorar a performance
CREATE INDEX idx_customer_email ON customer (email);
CREATE INDEX idx_payment_customer_id ON payment (customer_id);
CREATE INDEX idx_payment_payment_date ON payment (payment_date);
