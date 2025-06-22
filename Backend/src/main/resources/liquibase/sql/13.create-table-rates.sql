create table rates(
      rate_id bigserial primary key,
      city_id bigint not null references cities(city_id),
      rate_tier_id bigint not null references tiers(tier_id),
      init_price numeric not null,
      rate_per_km numeric not null,
      paid_waiting_per_minute numeric not null,
      free_time_in_seconds int not null default 120,
      constraint positive_price check(init_price > 0.0),
      constraint positive_distance_rate check(rate_per_km > 0.0),
      constraint positive_waiting_rate check(paid_waiting_per_minute > 0.0),
      unique (city_id, rate_tier_id)
);