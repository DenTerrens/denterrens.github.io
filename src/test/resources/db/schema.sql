create table if not exists customer_account (
    customer_id int primary key,
    email varchar(120) not null,
    status varchar(20) not null
);

create table if not exists api_audit (
    audit_id int primary key,
    source varchar(40) not null,
    payload_value varchar(200) not null
);
