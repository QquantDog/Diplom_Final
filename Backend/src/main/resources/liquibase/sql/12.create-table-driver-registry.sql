create table driver_registry(
        id bigserial primary key,
        driver_id bigint not null references driver_positions(driver_id),
        company_id bigint not null references taxi_companies(company_id),
        registration_date date not null default now()::date,
        registration_expiration_date date not null,
        unique(driver_id, company_id)
);