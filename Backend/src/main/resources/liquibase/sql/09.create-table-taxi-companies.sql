create table taxi_companies(
       company_id bigserial primary key,
       name varchar(100) not null unique,
       telephone varchar(100) not null,
       park_code varchar(20)
);