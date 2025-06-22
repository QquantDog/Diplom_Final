create table vehicles(
         vehicle_id bigserial primary key,
         brand_id bigint not null references vehicle_brands(brand_id),
         seats_number int not null,
         tier_id bigint not null references tiers(tier_id),
         vehicle_model varchar(200) not null
);