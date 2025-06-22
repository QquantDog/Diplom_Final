create type payment_method_type as enum ('card', 'cash');
create type match_request_type as enum ('OFFERED', 'DECLINED', 'ACCEPTED', 'STALE');