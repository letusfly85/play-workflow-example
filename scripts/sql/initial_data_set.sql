delete from workflow_statuses;

insert into workflow_statuses (id, name) values (1, '未着手');
insert into workflow_statuses (id, name) values (2, '進行中');
insert into workflow_statuses (id, name) values (3, '完了');

delete from workflow_definitions;

insert into workflow_definitions (id, workflow_id, name, status_id, scheme_step_id, scheme_step_label)
  values (1, 1, 'example', 1, 1, '商品未選択');

insert into workflow_definitions (id, workflow_id, name, status_id, scheme_step_id, scheme_step_label)
  values (2, 1, 'example', 2, 2, '注文内容確認');

insert into workflow_definitions (id, workflow_id, name, status_id, scheme_step_id, scheme_step_label)
  values (3, 1, 'example', 2, 3, '配送オプション選択');

insert into workflow_definitions (id, workflow_id, name, status_id, scheme_step_id, scheme_step_label)
  values (4, 1, 'example', 2, 4, '決済方法選択');

insert into workflow_definitions (id, workflow_id, name, status_id, scheme_step_id, scheme_step_label)
  values (5, 1, 'example', 3, 5, '購入完了');

delete from workflow_transitions;

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
  values (1, 'カートに入れる', 1, 2);

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
  values (1, '配送方法を決める', 2, 3);

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
  values (1, '決済方法を決める', 3, 4);

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
  values (1, '購入する', 4, 5);