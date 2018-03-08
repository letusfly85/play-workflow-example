ALTER TABLE workflow_transitions CHANGE task_id task_group_id INT;
ALTER TABLE workflow_transitions ADD is_defined BOOLEAN DEFAULT FALSE NOT NULL;

CREATE TABLE task_groups (
  id                INT AUTO_INCREMENT,
  task_id           INT NOT NULL,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
