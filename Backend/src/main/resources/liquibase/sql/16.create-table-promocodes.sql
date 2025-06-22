create table promocodes(
       promocode_id bigserial primary key,
       promocode_code varchar(100) not null unique,
       discount_value numeric not null,
       start_date date not null,
       end_date date not null,
       description text,
       constraint discount_percentile check (discount_value >= 0.0 and discount_value <= 100.0)
);