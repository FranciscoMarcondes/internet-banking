DELETE from TB_TRANSACTION where TRANSACTION_ID in (select TRANSACTION_ID from TB_TRANSACTION);
DELETE from tb_client where client_id in (select client_id from tb_client);

insert into tb_client (account, balance, birth_data, exclusive_plan, name, client_id) values ('123456', 1000, '1990-01-01', true, 'Teste 01', 1);
insert into tb_client (account, balance, birth_data, exclusive_plan, name, client_id) values ('654321', 2000, '1990-01-02', true, 'Teste 02', 2);
insert into tb_client (account, balance, birth_data, exclusive_plan, name, client_id) values ('112233', 3000, '1990-01-03', true, 'Teste 03', 3);
insert into tb_client (account, balance, birth_data, exclusive_plan, name, client_id) values ('223345', 4000, '1990-01-04', true, 'Teste 04', 4);
insert into tb_client (account, balance, birth_data, exclusive_plan, name, client_id) values ('654563', 5000, '1990-01-05', true, 'Teste 05', 5);

insert into TB_TRANSACTION (TRANSACTION_ID , BALANCE , DEPOSIT , TRANSACTION_DATE , WITHDRAW , CLIENT_CLIENT_ID ) values (1, 1000, 0, '2022-03-19', 10, 1);
insert into TB_TRANSACTION (TRANSACTION_ID , BALANCE , DEPOSIT , TRANSACTION_DATE , WITHDRAW , CLIENT_CLIENT_ID ) values (2, 2000, 0, '2022-03-18', 10, 2);
insert into TB_TRANSACTION (TRANSACTION_ID , BALANCE , DEPOSIT , TRANSACTION_DATE , WITHDRAW , CLIENT_CLIENT_ID ) values (3, 3000, 0, '2022-03-16', 10, 3);
insert into TB_TRANSACTION (TRANSACTION_ID , BALANCE , DEPOSIT , TRANSACTION_DATE , WITHDRAW , CLIENT_CLIENT_ID ) values (4, 4000, 0, '2022-03-19', 10, 4);
insert into TB_TRANSACTION (TRANSACTION_ID , BALANCE , DEPOSIT , TRANSACTION_DATE , WITHDRAW , CLIENT_CLIENT_ID ) values (5, 5000, 0, '2022-03-18', 10, 5);