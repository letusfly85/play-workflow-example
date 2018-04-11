CREATE TABLE order_actions (
  id                    INT AUTO_INCREMENT,
  name                  VARCHAR(300) NOT NULL,
  description           VARCHAR(300),
  service_id            INT NOT NULL DEFAULT 0,
  created_at            DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at            DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
