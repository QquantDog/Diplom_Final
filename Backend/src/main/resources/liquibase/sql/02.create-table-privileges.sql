create table "privileges"(
     privilege_id bigserial primary key,
     privilege_code varchar(100) unique not null
);