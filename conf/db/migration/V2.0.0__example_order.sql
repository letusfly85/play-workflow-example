CREATE TABLE orders (
  id                    INT AUTO_INCREMENT,
  transaction_id        VARCHAR(300),
  status_id             VARCHAR(300) NOT NULL,
  status_name           VARCHAR(300), -- replicate
  customer_name         VARCHAR(300),
  assigned_member_name  VARCHAR(300),
  service_id            INT NOT NULL DEFAULT 0,
  created_at            DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at            DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
