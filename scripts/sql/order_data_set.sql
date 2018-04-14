delete from orders;

-- --- --- ---

INSERT INTO workflow_example.orders
(id, order_id, transaction_id, status_id, status_name, customer_name, assigned_member_name, service_id)
VALUES(1, 'O0001', '', '', '', '山田さん', '田中さん', 0);
INSERT INTO workflow_example.orders
(id, order_id, transaction_id, status_id, status_name, customer_name, assigned_member_name, service_id)
VALUES(2, 'O0002', '', '', '', '太田さん', '伊藤さん', 0);
INSERT INTO workflow_example.orders
(id, order_id, transaction_id, status_id, status_name, customer_name, assigned_member_name, service_id)
VALUES(3, 'O0003', '', '', '', '鈴木さん', '鈴木さん', 0);
