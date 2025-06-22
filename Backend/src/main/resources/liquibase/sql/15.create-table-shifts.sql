create table shifts(
       shift_id bigserial primary key,
       city_rate_id bigint not null references rates(rate_id),
       cab_id bigint not null references cabs(cab_id),
       driver_id bigint not null references driver_positions(driver_id),
       starttime timestamp with time zone not null default now(),
       endtime timestamp with time zone
);