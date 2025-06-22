create table match_requests(
     match_id bigserial primary key,
     ride_id bigint not null references rides(ride_id),
     shift_id bigint not null references shifts(shift_id),
     init_distance numeric not null,
     match_request_status match_request_type,
     created_at timestamp with time zone not null default now()
);