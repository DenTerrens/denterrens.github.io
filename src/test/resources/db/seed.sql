delete from customer_account;
delete from api_audit;

insert into customer_account (customer_id, email, status) values
(1001, 'standard_user@demo.local', 'ACTIVE'),
(1002, 'problem_user@demo.local', 'LOCKED'),
(1003, 'api_auditor@demo.local', 'ACTIVE');
