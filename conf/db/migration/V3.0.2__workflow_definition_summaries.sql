CREATE TABLE workflow_definition_summaries (
  id                INT AUTO_INCREMENT,
  workflow_id       INT NOT NULL,
  name              VARCHAR(300) NOT NULL,
  service_id        INT NOT NULL,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
