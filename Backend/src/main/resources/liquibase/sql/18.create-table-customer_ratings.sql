create table customer_ratings(
     rating_id bigserial primary key,
     ride_id bigint unique not null references rides(ride_id),
     rating numeric,
     created_at timestamp with time zone not null default now(),
     updated_at timestamp with time zone,
     comment text,
     constraint rating_between_1_and_5 check (rating >= 1.0 and rating <= 5.0)
);