delete from workflow_statuses;

insert into workflow_statuses (id, name) values (1, '未着手');
insert into workflow_statuses (id, name) values (2, '進行中');
insert into workflow_statuses (id, name) values (3, '完了');

-- --- --- ---

delete from workflow_definitions;

insert into workflow_definitions (id, workflow_id, name, status_id, step_id, step_label)
  values (1, 1, 'example', 1, 1, '商品未選択');

insert into workflow_definitions (id, workflow_id, name, status_id, step_id, step_label)
  values (2, 1, 'example', 2, 2, '注文内容確認');

insert into workflow_definitions (id, workflow_id, name, status_id, step_id, step_label)
  values (3, 1, 'example', 2, 3, '配送オプション選択');

insert into workflow_definitions (id, workflow_id, name, status_id, step_id, step_label)
  values (4, 1, 'example', 2, 4, '決済方法選択');

insert into workflow_definitions (id, workflow_id, name, status_id, step_id, step_label)
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

-- --- --- --- --- --- --- ---
-- --- --- --- --- --- --- ---


insert into workflow_definitions (workflow_id, name, status_id, step_id, step_label, is_first_step)
values (2, 'example.order', 1, 1, '未着手', 1);

insert into workflow_definitions (workflow_id, name, status_id, step_id, step_label)
values (2, 'example.order', 2, 3, '準備中');

insert into workflow_definitions (workflow_id, name, status_id, step_id, step_label)
values (2, 'example.order', 2, 5, '配送準備完了');

insert into workflow_definitions (workflow_id, name, status_id, step_id, step_label, is_last_step)
values (2, 'example.order', 3, 7, '配送完了', 1);

-- --- --- --- --- --- --- ---

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
values (2, '進行中にする', 1, 3);

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
values (2, '配送準備を完了する', 3, 5);

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
values (2, '配送する', 5, 7);

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
values (2, '見直しをする', 5, 3);

-- --- --- --- --- --- --- ---
-- --- --- --- --- --- --- ---


insert into workflow_definitions (workflow_id, name, status_id, step_id, step_label, is_first_step)
values (3, 'example.craft', 1, 1, '未着手', 1);

insert into workflow_definitions (workflow_id, name, status_id, step_id, step_label)
values (3, 'example.craft', 2, 3, '設計中');

insert into workflow_definitions (workflow_id, name, status_id, step_id, step_label)
values (3, 'example.craft', 2, 5, '組立中');

insert into workflow_definitions (workflow_id, name, status_id, step_id, step_label)
values (3, 'example.craft', 2, 6, '塗装中');

insert into workflow_definitions (workflow_id, name, status_id, step_id, step_label, is_last_step)
values (3, 'example.craft', 3, 7, '制作完了', 1);

-- --- --- --- --- --- --- ---

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
values (3, '設計を始める', 1, 3);

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
values (3, '組み立てを始める', 3, 5);

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
values (3, '塗装業者に委託する', 5, 6);

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
values (3, '納品する', 6, 7);

insert into workflow_transitions (workflow_id, name, from_step_id, to_step_id)
values (3, '組み立てからやり直す', 6, 3);
