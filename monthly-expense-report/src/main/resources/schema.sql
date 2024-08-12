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

CREATE TABLE invoice_request
(
    id               INT AUTO_INCREMENT PRIMARY KEY,                     -- Identificador único da requisição
    customer_id      INT            NOT NULL,                            -- Identificador do cliente (chave estrangeira)
    status           VARCHAR(50) DEFAULT 'Pending',                      -- Status da análise (Pending, In Review, Completed)
    submitted_at     TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,              -- Data de submissão
    analysis_results TEXT,                                               -- Resultados da análise
    amount           DECIMAL(10, 2) NOT NULL,                            -- Valor da fatura
    token_count      INT         DEFAULT 0,                              -- Quantidade de tokens identificados
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE -- Relacionamento com a tabela customer
);


-- Criando tabela payment
CREATE TABLE payment
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,                                 -- Identificador único do pagamento
    invoice_request_id INT            NOT NULL,                                        -- Identificador da requisição de fatura (chave estrangeira)
    payment_amount     DECIMAL(10, 2) NOT NULL,                                        -- Valor pago pelo cliente
    payment_date       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,                          -- Data e hora do pagamento
    payment_method     VARCHAR(50) DEFAULT 'Credit Card',                              -- Método de pagamento (ex: cartão de crédito, cartão de débito)
    card_number        VARCHAR(20)    NOT NULL,                                        -- Número do cartão (armazenado de forma segura)
    card_holder_name   VARCHAR(100)   NOT NULL,                                        -- Nome do titular do cartão
    card_expiry_date   DATE           NOT NULL,                                        -- Data de expiração do cartão
    card_cvv           VARCHAR(4)     NOT NULL,                                        -- CVV do cartão (armazenado de forma segura)
    token_count        INT         DEFAULT 0,                                          -- Quantidade de tokens utilizados neste pagamento
    payment_status     VARCHAR(50) DEFAULT 'Processing',                               -- Status do pagamento (Processing, Completed, Failed)
    FOREIGN KEY (invoice_request_id) REFERENCES invoice_request (id) ON DELETE CASCADE -- Relacionamento com a tabela invoice_request
);


-- Criando índices para melhorar a performance
CREATE INDEX idx_customer_email ON customer (email);
CREATE INDEX idx_invoice_request_status ON invoice_request (status);
CREATE INDEX idx_payment_invoice_request_id ON payment (invoice_request_id);
