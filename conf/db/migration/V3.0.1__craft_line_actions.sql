CREATE TABLE craft_line_actions (
  id                    INT AUTO_INCREMENT,
  name                  VARCHAR(300) NOT NULL,
  description           VARCHAR(300),
  service_id            INT NOT NULL DEFAULT 0,
  created_at            DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at            DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE action_transactions (
  id                    INT AUTO_INCREMENT,
  action_id             INT NOT NULL,
  workflow_id           INT NOT NULL,
  transaction_id        VARCHAR(128) NOT NULL,
  step_id               INT NOT NULL,
  is_reverted           BOOLEAN NOT NULL DEFAULT FALSE,
  created_at            DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at            DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE workflow_action_conditions (
  id                INT AUTO_INCREMENT,
  workflow_id       INT NOT NULL,
  transition_id     INT NOT NULL,
  action_id         INT NOT NULL,
  service_id        INT NOT NULL DEFAULT 0,
  name              VARCHAR(200),
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
