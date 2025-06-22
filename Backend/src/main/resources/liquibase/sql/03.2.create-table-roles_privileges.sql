create table roles_privileges_join(
	id bigserial primary key,
	privilege_id bigint not null references "privileges"(privilege_id),
	role_id bigint not null references roles(role_id),
	unique(privilege_id, role_id)
);