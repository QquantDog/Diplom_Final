create table tiers(
       tier_id bigserial primary key,
       tier_name varchar(50) unique not null,
       description text
);