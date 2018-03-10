CREATE TABLE workflow_conditions (
  id                INT AUTO_INCREMENT,
  name              VARCHAR(200),
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE workflow_condition_suites (
  id                     INT AUTO_INCREMENT,
  suite_id               INT NOT NULL,
  workflow_condition_id  INT NOT NULL,
  condition_group_code   VARCHAR(300) NOT NULL,
  conditions_expr        VARCHAR(300) NOT NULL,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

ALTER TABLE workflow_transitions CHANGE task_group_id condition_suite_id INT;
