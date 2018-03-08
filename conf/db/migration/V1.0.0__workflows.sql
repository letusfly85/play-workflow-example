CREATE TABLE workflow_statuses (
  id                INT AUTO_INCREMENT,
  name              VARCHAR(300) NOT NULL,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE workflow_schemes (
  id                INT AUTO_INCREMENT,
  workflow_id       INT NOT NULL,
  name              VARCHAR(300) NOT NULL,
  status_id         INT NOT NULL,
  scheme_step_id    INT NOT NULL,
  scheme_step_label VARCHAR(300) NOT NULL,
  is_first_step     BOOLEAN NOT NULL DEFAULT FALSE,
  is_last_step      BOOLEAN NOT NULL DEFAULT FALSE,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uq_idx_workflow_scheme (workflow_id, scheme_step_id)
);

CREATE TABLE workflow_transitions (
  id                INT AUTO_INCREMENT,
  workflow_id       INT NOT NULL,
  name              VARCHAR(300) NOT NULL,
  from_step_id      INT NOT NULL,
  to_step_id        INT NOT NULL,
  task_id           INT NOT NULL,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uq_idx_workflow_transition (workflow_id, from_step_id, to_step_id)
);

CREATE TABLE tasks (
  id                INT AUTO_INCREMENT,
  name              VARCHAR(300) NOT NULL,
  description       VARCHAR(2000),
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
