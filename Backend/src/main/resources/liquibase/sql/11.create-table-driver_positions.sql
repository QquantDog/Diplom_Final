create table driver_positions(
    driver_id bigint primary key references users(user_id) on delete cascade,
    is_on_shift bool not null,
    is_on_ride bool not null,
    current_point geometry(Point, 4326)
);