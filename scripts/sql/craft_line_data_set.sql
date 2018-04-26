delete from craft_lines;

-- --- --- ---

INSERT INTO workflow_example.craft_lines
(id, transaction_id, status_id, status_name, product_name, assigned_member_name, service_id)
VALUES(1, '', '', '', '山田さん', '花瓶', 0);
INSERT INTO workflow_example.craft_lines
(id, transaction_id, status_id, status_name, product_name, assigned_member_name, service_id)
VALUES(2, '', '', '', '太田さん', '本棚', 0);
INSERT INTO workflow_example.craft_lines
(id, transaction_id, status_id, status_name, product_name, assigned_member_name, service_id)
VALUES(3, '', '', '', '鈴木さん', 'マグカップ', 0);
