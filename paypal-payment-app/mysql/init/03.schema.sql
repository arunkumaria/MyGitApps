-- Use the database
CREATE DATABASE IF NOT EXISTS payments;
USE payments;

-- Payment_Method table
CREATE TABLE IF NOT EXISTS Payment_Method (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  status TINYINT DEFAULT 1,
  creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
  PRIMARY KEY(id)
);

-- Payment_Type table
CREATE TABLE IF NOT EXISTS Payment_Type (
  id INT NOT NULL AUTO_INCREMENT,
  type VARCHAR(50) NOT NULL,
  status TINYINT DEFAULT 1,
  creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
  PRIMARY KEY(id)
);

-- Provider table
CREATE TABLE IF NOT EXISTS Provider (
  id INT NOT NULL AUTO_INCREMENT,
  providerName VARCHAR(50) NOT NULL,
  status TINYINT DEFAULT 1,
  creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
  PRIMARY KEY(id)
);

-- Transaction_Status table
CREATE TABLE IF NOT EXISTS Transaction_Status (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  status TINYINT DEFAULT 1,
  creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
  PRIMARY KEY(id)
);

-- Transaction table
CREATE TABLE IF NOT EXISTS `Transaction` (
  id INT NOT NULL AUTO_INCREMENT,
  userId INT NOT NULL,
  paymentMethodId INT NOT NULL,
  providerId INT NOT NULL,
  paymentTypeId INT NOT NULL,
  txnStatusId INT NOT NULL,
  amount DECIMAL(19,2) DEFAULT 0.00,
  currency VARCHAR(3) NOT NULL,
  merchantTransactionReference VARCHAR(50) NOT NULL,
  txnReference VARCHAR(50) NOT NULL,
  providerReference VARCHAR(100),
  errorCode VARCHAR(500),
  errorMessage VARCHAR(1000),
  creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
  retryCount INT DEFAULT 0,
  PRIMARY KEY(id),
  UNIQUE KEY transaction_txnReference (txnReference),
  KEY transaction_paymentMethodId (paymentMethodId),
  KEY transaction_providerId (providerId),
  KEY transaction_txnStatusId (txnStatusId),
  KEY transaction_paymentTypeId (paymentTypeId),
  CONSTRAINT fk_transaction_paymentMethod FOREIGN KEY (paymentMethodId) REFERENCES Payment_Method(id),
  CONSTRAINT fk_transaction_provider FOREIGN KEY (providerId) REFERENCES Provider(id),
  CONSTRAINT fk_transaction_txnStatus FOREIGN KEY (txnStatusId) REFERENCES Transaction_Status(id),
  CONSTRAINT fk_transaction_paymentType FOREIGN KEY (paymentTypeId) REFERENCES Payment_Type(id)
);

-- Transaction_Log table
CREATE TABLE IF NOT EXISTS Transaction_Log (
  id INT NOT NULL AUTO_INCREMENT,
  transactionId INT NOT NULL,
  txnFromStatus VARCHAR(50) DEFAULT '-1',
  txnToStatus VARCHAR(50) DEFAULT '-1',
  creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
  PRIMARY KEY(id),
  KEY transaction_log_transactionId (transactionId),
  CONSTRAINT fk_transaction_log_transaction FOREIGN KEY(transactionId) REFERENCES `Transaction`(id)
);
