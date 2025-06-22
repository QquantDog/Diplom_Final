create table vehicle_brands(
       brand_id bigserial primary key,
       brand_name varchar(200) unique not null
);