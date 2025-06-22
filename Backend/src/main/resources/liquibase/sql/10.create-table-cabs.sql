create table cabs(
     cab_id bigserial primary key,
     registered_company_id bigint not null references taxi_companies(company_id),
     is_on_shift boolean not null,
     vin varchar(17) unique not null,
     manufacture_date date not null,
     color_description varchar(100) not null,
     vehicle_id bigserial not null references vehicles(vehicle_id),
     license_plate varchar(50) not null unique
);