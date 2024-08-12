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
    due_date         DATE,                                               -- Data de vencimento
    token_count      INT         DEFAULT 0,                              -- Quantidade de tokens identificados
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE -- Relacionamento com a tabela customer
);


-- Criando tabela payment
CREATE TABLE payment
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,                                 -- Identificador único do pagamento
    invoice_request_id INT            NOT NULL,                                        -- Identificador da requisição de pagamento (chave estrangeira)
    amount             DECIMAL(10, 2) NOT NULL,                                        -- Valor do pagamento
    payment_date       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,                          -- Data e hora do pagamento
    payment_method     VARCHAR(50),                                                    -- Método de pagamento (ex: cartão de crédito, transferência bancária)
    status             VARCHAR(20) DEFAULT 'Pending',-- Status do pagamento (ex: Pending, Completed, Failed)
    token_count        INT         DEFAULT 0,                                          -- Quantidade de tokens utilizados neste pagamento
    FOREIGN KEY (invoice_request_id) REFERENCES invoice_request (id) ON DELETE CASCADE -- Relacionamento com a tabela invoice_request
);




-- Criando índices para melhorar a performance
CREATE INDEX idx_customer_email ON customer(email);
CREATE INDEX idx_invoice_request_status ON invoice_request(status);
CREATE INDEX idx_payment_status ON payment(status);
