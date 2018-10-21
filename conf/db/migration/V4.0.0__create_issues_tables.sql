CREATE TABLE issues (
  id                    INT AUTO_INCREMENT,
  issue_id              VARCHAR(128) UNIQUE NOT NULL,
  transaction_id        VARCHAR(300),
  status_id             VARCHAR(300) NOT NULL,
  status_name           VARCHAR(300), -- replicate
  service_id            VARCHAR(300),
  project_id            VARCHAR(300),
  user_id               VARCHAR(300),
  created_at            DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at            DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

