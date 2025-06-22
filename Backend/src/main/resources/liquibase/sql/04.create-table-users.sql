create table users (
       user_id bigserial primary key,
       role_id bigint not null references roles(role_id),
       first_name varchar(100) not null,
       last_name varchar(100),
       email varchar(100) unique not null,
       hashed_password varchar(300) not null,
       phone_number varchar(30) unique not null,
       registration_date timestamp with time zone not null default now()
);