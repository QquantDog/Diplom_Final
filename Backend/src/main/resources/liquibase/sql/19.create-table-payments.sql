create table payments(
     payment_id bigserial primary key,
     ride_id bigint unique not null references rides(ride_id),
     "method" payment_method_type,
     overall_price numeric,
     constraint overall_price_positive check (overall_price > 0.0)
);