-- 1. CUSTOMERS
CREATE TABLE IF NOT EXISTS customers (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS bank_accounts (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type VARCHAR(20) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    customer_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_account_customer
    FOREIGN KEY (customer_id)
    REFERENCES customers(id),

    CONSTRAINT chk_account_type
    CHECK (account_type IN ('CHECKING', 'SAVINGS')),

    CONSTRAINT chk_account_balance
    CHECK (balance >= 0)
    );

CREATE TABLE IF NOT EXISTS transactions (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    description VARCHAR(255),
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    bank_account_id BIGINT NOT NULL,

    CONSTRAINT fk_transaction_account
    FOREIGN KEY (bank_account_id)
    REFERENCES bank_accounts(id),

    CONSTRAINT chk_transaction_type
    CHECK (
              transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')
    ),

    CONSTRAINT chk_transaction_amount
    CHECK (amount > 0)
    );

