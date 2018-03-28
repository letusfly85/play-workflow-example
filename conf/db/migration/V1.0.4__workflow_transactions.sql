ALTER TABLE workflow_definitions CHANGE scheme_step_id step_id INT NOT NULL;
ALTER TABLE workflow_definitions CHANGE scheme_step_label step_label VARCHAR(300) NOT NULL;


CREATE TABLE workflow_transactions (
  id                  INT AUTO_INCREMENT,
  workflow_id         INT NOT NULL,
  transaction_id      VARCHAR(300) NOT NULL,
  user_id             VARCHAR(300),
  step_id             INT NOT NULL,
  from_transition_id  INT,
  is_init             BOOLEAN NOT NULL DEFAULT FALSE,
  is_completed        BOOLEAN NOT NULL DEFAULT FALSE,
  created_at          DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
