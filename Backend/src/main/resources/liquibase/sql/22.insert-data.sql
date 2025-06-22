insert into tiers(tier_name, description) values('economic', 'best balance between comfort and price'),
                                                     ('comfort',  'comfort description'),
                                                     ('lux', 	  'top-tier service');

insert into vehicle_brands(brand_name) values('Audi'),('Volkswagen'),('Skoda'),('Toyota');

insert into vehicles(brand_id, vehicle_model, tier_id, seats_number) values(1, 'TT', 1, 4),(2, 'Passat', 1, 4),(3, 'Rapid', 2, 4),(4, 'Corolla', 2, 4);

insert into "privileges"(privilege_id, privilege_code) values
    (1, 'USER_READ_ALL'), (2, 'USER_DEL'),
--  DRiverPosi
    (3, 'DRIV_READ_ALL'), (4, 'DRIV_READ_OWN'),
    (5, 'DRIV_UPD_POS'),
--  DrivREgis
    (6, 'DRIVREG_READ_ALL'), (7, 'DRIVREG_READ_OWN'),
    (8, 'DRIVREG_CREATE_REG'), (9, 'DRIVREG_DEL_REG'),
--    TaxiCompanies
    (10, 'TC_READ_ALL'), (11, 'TC_READ_CABS'),
    (12, 'TC_CREATE'), (13, 'TC_UPD'), (14, 'TC_DEL'),
--    cabs
    (15, 'CAB_READ_OWN'), (16, 'CAB_CRUD_ADMIN'),
--    vehicles
    (17, 'VEHICLE_READ_ALL'), (18, 'VEHICLE_CRUD'),
--  rates
    (19, 'RATE_CRUD'),
--  shifts
    (20, 'SHIFT_GET_ALL'), (21, 'SHIFT_GET_OWN'),
    (22, 'SHIFT_PROCESS'),
-- cities
    (23, 'CITY_CRUD'),
-- tiers
    (24, 'TIER_CRUD'),
-- promocodes
    (25, 'PROMO_CRUD'), (26, 'PROMO_READ_ALL'),
--  cRatings
    (27, 'RATING_READ_ALL'), (28, 'RATING_READ_OWN'),
    (29, 'RATING_PROCESS_CUSTOMER'), (30, 'RATING_DEL_ADMIN'),
--    matchRequests
    (31, 'MR_READ_ALL'), (32, 'MR_PROCESS'),
--    payments
    (33, 'PAYMENT_READ_ALL'), (34, 'PAYMENT_READ_DRIVER'), (35, 'PAYMENT_READ_CUSTOMER'),
--    rides
    (36, 'RIDE_READ_ALL'), (37, 'RIDE_READ_CUSTOMER'), (38, 'RIDE_READ_DRIVER'),
    (39, 'RIDE_INIT'), (40, 'RIDE_MATCH_PROCESS'), (41, 'RIDE_STATE_PROCESS'),
    (42, 'RIDE_OWN_CANCEL'), (43, 'RIDE_ACTIVATE_PROMO'), (44, 'RIDE_TIP')
    ;

insert into roles(role_id, role_name) values(1, 'customer'), (2, 'driver'), (3, 'admin');

insert into roles_privileges_join(role_id, privilege_id) values
    (3, 1),(3, 2),
--    driver
    (3, 3),(2, 4),
    (2, 5),
--  drivRegis
    (3, 6),(2, 7),(2, 8),(2, 9),
-- taxiCompanies
    (3, 10), (2, 10), (3, 11), (2, 11),
    (3, 12), (3, 13), (3, 14),
-- cabs
    (2, 15), (3, 16),
--    vehicles
    (2, 17), (3, 17), (3, 18),
--    rate
    (3, 19),
--    shifts
    (3, 20), (2, 21), (2, 22),
--  cites/tiers
    (3, 23), (3, 24),
--  promocodes
    (3, 25), (3, 26),
--    cRatings
    (3, 27), (1, 28),
    (1, 29), (3, 30),
--  matchRequests
    (3, 31), (3, 32),
--    paymentes
    (3, 33), (2, 34), (1, 35),
--    rides
    (3, 36), (1, 37), (2, 38),
    (1, 39), (2, 40), (2, 41),
    (1, 42), (1, 43), (1, 44)
    ;



insert into cities(city_name) values('Grodno'), ('Minsk');

insert into rates(city_id, rate_tier_id, init_price, rate_per_km, paid_waiting_per_minute, free_time_in_seconds)
values(1, 1, 2.0, 1.0, 0.4, 120),(2, 1, 3.0, 1.5, 0.6, 120),
      (1, 2, 2.2, 1.2, 0.45, 180),(2, 2, 3.3, 1.8, 0.7, 180);

insert into users(role_id, first_name, last_name, email, hashed_password, phone_number, registration_date) values
--                                                                                                                                                'pwd'
                                                                                                               (1, 'Max', 'qqq', 'max@gmail.com', '$2a$10$8Mh.Da5N5qfDbJiHX4xITOK8xrtH.vqYEojgCsDRyKjmS1jpvrzFy', '+375-22-341-53-21', now() - interval '5 months'),
                                                                                                               (1, 'Vlad', 'www', 'vlad@gmail.com', '$2a$10$8Mh.Da5N5qfDbJiHX4xITOK8xrtH.vqYEojgCsDRyKjmS1jpvrzFy', '+375-44-453-91-88', now() - interval '7 months'),

                                                                                                               (2, 'Oleg', 'aaa', 'pleg@gmail.com', '$2a$10$8Mh.Da5N5qfDbJiHX4xITOK8xrtH.vqYEojgCsDRyKjmS1jpvrzFy', '+375-72-3324214', now() - interval '12 months'),
                                                                                                               (2, 'Grisha', 'ccc', 'grisha@gmail.com', '$2a$10$8Mh.Da5N5qfDbJiHX4xITOK8xrtH.vqYEojgCsDRyKjmS1jpvrzFy', '+123-45-678-90-12', now() - interval '15 months'),

                                                                                                               (2, 'Driver_1', 'vvv', 'driver1@gmail.com', '$2a$10$8Mh.Da5N5qfDbJiHX4xITOK8xrtH.vqYEojgCsDRyKjmS1jpvrzFy', '+123-63-173-90-12', now() - interval '15 months'),
                                                                                                               (2, 'Driver_2', 'cvx', 'driver2@gmail.com', '$2a$10$8Mh.Da5N5qfDbJiHX4xITOK8xrtH.vqYEojgCsDRyKjmS1jpvrzFy', '+321-45-873-90-12', now() - interval '14 months'),

--                                                                                                                                                       'admin_pwd'
                                                                                                               (3, 'Admin', '----', 'admin@gmail.com', '$2a$10$bOKppAh/CMiIcNopnpjRS.UA1LZGMbhnTpmcvDDpk0qRg2OoU6eni', '+324-53-23-52-52', now() - interval '3 months'),
--                                                                                                                                                        'blank' from TestDriver till NearDriver2
                                                                                                               (2, 'TestDriver', 'hmty', 'wow@gmail.com', '$2a$10$vx7ikZ4bOj5E3AQ0i/6TCOJ.WuRMvqbIOpRchL1n3.EWvAzJ6axFO', '+645-22-341-53-21', now() - interval '4 months'),
                                                                                                               (1, 'TestCustomer', 'hjkt', 'meow@gmail.com', '$2a$10$vx7ikZ4bOj5E3AQ0i/6TCOJ.WuRMvqbIOpRchL1n3.EWvAzJ6axFO', '+456-22-341-53-21', now() - interval '7 months'),
                                                                                                               (2, 'NearDriver1', 'oooo', 'wow1@gmail.com', '$2a$10$vx7ikZ4bOj5E3AQ0i/6TCOJ.WuRMvqbIOpRchL1n3.EWvAzJ6axFO', '+645-22-b41-53-21', now() - interval '4 months'),
                                                                                                               (2, 'NearDriver2', 'oooo', 'wow2@gmail.com', '$2a$10$vx7ikZ4bOj5E3AQ0i/6TCOJ.WuRMvqbIOpRchL1n3.EWvAzJ6axFO', '+645-22-a41-53-21', now() - interval '4 months');

insert into driver_positions(driver_id, is_on_shift, is_on_ride, current_point) values
                                                                                  (3, false, false, null),
                                                                                  (4, false, false, null),
                                                                                  (5, true, false, 'POINT(54.6126 23.5126)'),
                                                                                  (6, true, true, 'POINT(54.3562 23.8543)'),
                                                                                  (8, false, false, null),
                                                                                  (10, true, false, 'POINT(54.3 23.5)'),
                                                                                  (11, true, false, 'POINT(54.35 23.6)');

insert into taxi_companies(name, telephone, park_code) values('First taxopark_1', '+234-432-34-23', 7320), ('Second_taxopark_2', '+aaaaaa', 4954);

insert into driver_registry(driver_id, company_id, registration_date, registration_expiration_date) values(3, 1, now()::date - '5 year'::interval, now()::date + '5 year'::interval), (4, 2, now()::date - '5 year'::interval, now()::date + '5 year'::interval),
                                                                                                          (5, 1, now()::date - '5 year'::interval, now()::date + '5 year'::interval), (6, 1, now()::date - '5 year'::interval, now()::date + '5 year'::interval),
                                                                                                          (3, 2, now()::date - '5 year'::interval, now()::date + '5 year'::interval),
                                                                                                          (10, 2, now()::date - '5 year'::interval, now()::date + '5 year'::interval), (10, 1, now()::date - '5 year'::interval, now()::date + '5 year'::interval),
                                                                                                          (11, 2, now()::date - '5 year'::interval, now()::date + '5 year'::interval), (11, 1, now()::date - '5 year'::interval, now()::date + '5 year'::interval);

-- текущие такси
insert into cabs(registered_company_id, is_on_shift, vin, manufacture_date, color_description, vehicle_id, license_plate) values
                                                                                                                              (1, false, 'vin1', '2020-01-02'::date, 'yellow',  1, '1234-mb-1'),
                                                                                                                              (1, true, 'vin2', '2016-02-03'::date, 'black',  2, '4321-hy-4'),
                                                                                                                              (2, false, 'vin3', '2019-04-05'::date, 'gray',  3, '5631-mb-1'),
                                                                                                                              (2, false, 'vin4', '2020-01-01'::date, 'yellow', 1, '1235-mb-2'),
                                                                                                                              (1, true, 'vin5', '2020-01-01'::date, 'white', 1, '1235-mb-3'),
                                                                                                                              (2, true, 'vin6', '2020-01-01'::date, 'white', 2, '1235-mb-4');

insert into shifts(city_rate_id, cab_id, driver_id, starttime, endtime) values
                                                                       (1, 1, 3, '2020-01-01 09:10:10'::timestamp, '2020-01-01 15:10:10'::timestamp),
                                                                       (2, 2, 3, '2020-01-02 10:10:10'::timestamp, '2020-01-02 18:10:10'::timestamp),
                                                                       (3, 3, 4, '2021-03-01 14:00:00'::timestamp, '2021-03-01 21:00:00'::timestamp),
                                                                       (4, 3, 4, '2021-03-01 14:00:00'::timestamp, '2021-03-01 21:00:00'::timestamp),
                                                                       (1, 2, 5, now() - interval '180 min', null), -- этот в активном поиске
                                                                       (2, 1, 6, now() - interval '120 min', now() - interval '20 min'),
                                                                       (2, 5, 10, now() - interval '180 min', null), -- этот в активном поиске
                                                                       (2, 6, 11, now() - interval '120 min', null); -- этот везет пассажира

insert into promocodes(promocode_code, discount_value, start_date, end_date, description) values
                                                                                              ('DISCOUNT20', 0.2, '2025-01-01'::date, '2025-01-03'::date, '20 percent discount'),
                                                                                              ('DISCOUNT30', 0.3, '2025-05-01'::date, '2025-05-03'::date, '30 percent discount'),

                                                                                              ('PaginationTest3', 0.2, '2025-01-01'::date, '2025-01-03'::date, '20 percent discount'),
                                                                                              ('PaginationTest4', 0.3, '2025-05-01'::date, '2025-05-03'::date, '30 percent discount'),
                                                                                              ('PaginationTest5', 0.2, '2025-01-01'::date, '2025-01-03'::date, '20 percent discount'),
                                                                                              ('PaginationTest6', 0.3, '2025-05-01'::date, '2025-05-03'::date, '30 percent discount'),
                                                                                              ('PaginationTest7', 0.2, '2025-01-01'::date, '2025-01-03'::date, '20 percent discount'),
                                                                                              ('PaginationTest8', 0.3, '2025-05-01'::date, '2025-05-03'::date, '30 percent discount'),
                                                                                              ('PaginationTest9', 0.2, '2025-01-01'::date, '2025-01-03'::date, '20 percent discount'),
                                                                                              ('PaginationTest10', 0.3, '2025-05-01'::date, '2025-05-03'::date, '30 percent discount');




-- пока всего 2 кастомера - будет 1 активный, 2 поиске - потом добавим и пойдет
insert into rides(customer_id, shift_id, promocode_id, accepted_rate_id, ride_tip, ride_distance_expected_meters, ride_distance_actual_meters, start_point, end_point, created_at, ride_accepted_at, ride_driver_waiting, ride_starttime, ride_endtime, status, ride_actual_price) values
                                                                                                                                                                                                                                                                                        (1, 1, null, 1, 2.5,  5500.0, 5675.0,  'POINT(53.34 23.49)', 'POINT(53.65 23.43)', '2020-01-01 12:00:00', '2020-01-01 12:05:00', '2020-01-01 12:06:00', '2020-01-01 12:15:00', '2020-01-01 12:30:00', 'completed',  5.6),
                                                                                                                                                                                                                                                                                        (1, 1, 1,    2, null, 4500.0, 4489.0, 'POINT(53.14 23.38)', 'POINT(53.17 23.42)', '2020-01-01 14:00:00', '2020-01-01 14:07:30', '2020-01-01 14:10:50', '2020-01-01 14:17:20', '2020-01-01 14:28:20', 'completed',  7.2),
                                                                                                                                                                                                                                                                                        (2, 3, null, 3, 2.0,  4600.0, 4710.0,  'POINT(53.86 27.23)', 'POINT(53.79 27.54)', '2021-03-01 15:00:00', '2021-03-01 15:07:30', '2021-03-01 15:10:50', '2021-03-01 15:16:20', '2021-03-01 15:23:20', 'completed',  6.8),
                                                                                                                                                                                                                                                                                        (1, 5, null, 4, null, 5500.0, null,   'POINT(53.34 23.49)', 'POINT(53.65 23.43)', now() - interval '3 min', null, null, null, null, 'accepted', null),
                                                                                                                                                                                                                                                                                        (2, 6, null, 1, null, 4500.0, null,   'POINT(53.14 23.38)', 'POINT(53.17 23.42)', now() - interval '12 min', now() - interval '5 min', now() - interval '2 min', now() - interval '2 min', null, 'in-way', null);

insert into customer_ratings(rating, ride_id, created_at, updated_at, "comment") values
                                                                            (5.0, 1, '2020-01-02 10:00:00', null, 'отличная поездка'),
                                                                            (4.0, 2, '2020-01-01 14:23:20', null, 'непристегнутый ремень у водителя'),
                                                                            (4.9, 3, '2022-01-02 10:00:00', null, 'вежливый водитель'),
                                                                            (4.8, 5, now()                , null, 'небольщая задержка');

insert into payments(ride_id, method, overall_price) values
                                                 (1, 'cash', 8.0),
                                                 (2, 'card', 7.4),
                                                 (3, 'cash', 8.7),
                                                 (4, 'cash', null),
                                                 (5, 'cash', null);