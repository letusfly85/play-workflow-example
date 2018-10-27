ALTER TABLE workflow_transitions ADD display_order INT NOT NULL DEFAULT 0 AFTER name;

ALTER TABLE workflow_details RENAME workflow_steps;
