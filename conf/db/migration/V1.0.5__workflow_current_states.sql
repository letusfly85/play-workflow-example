CREATE TABLE workflow_current_states (
  id                  INT AUTO_INCREMENT,
  workflow_id         INT NOT NULL,
  transaction_id      VARCHAR(300) NOT NULL,
  user_id             VARCHAR(300),
  current_step_id     INT NOT NULL,
  scheme_id           INT NOT NULL DEFAULT 0,
  service_id          INT NOT NULL DEFAULT 0,
  created_at          DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
