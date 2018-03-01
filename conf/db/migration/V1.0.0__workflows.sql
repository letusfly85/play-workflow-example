CREATE TABLE workflow_status (
  id                INT AUTO_INCREMENT,
  name              VARCHAR(300) NOT NULL,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE workflow_scheme (
  id                INT AUTO_INCREMENT,
  workflow_id       INT NOT NULL,
  name              VARCHAR(300) NOT NULL,
  status_id         VARCHAR(300) NOT NULL,
  scheme_step_id    INT NOT NULL,
  scheme_step_label VARCHAR(300) NOT NULL,
  is_first_step     BOOLEAN NOT NULL DEFAULT FALSE,
  is_last_step      BOOLEAN NOT NULL DEFAULT FALSE,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uq_idx_workflow_scheme (workflow_id, scheme_step_id)
);

CREATE TABLE workflow_transition (
  id                INT AUTO_INCREMENT,
  workflow_id       INT NOT NULL,
  name              VARCHAR(300) NOT NULL,
  status_id         VARCHAR(300) NOT NULL,
  from_step_id      INT NOT NULL,
  to_step_id        INT NOT NULL,
  condition_type    VARCHAR(300) NOT NULL,
  condition_key     VARCHAR(300),
  condition_value   VARCHAR(300),
  condition_expr    VARCHAR(300),
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uq_idx_workflow_transition (workflow_id, from_step_id, to_step_id)
);
