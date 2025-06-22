create domain user_role as varchar(20) not null check (value in ('customer', 'driver', 'admin'));
create domain ride_status as varchar(20) not null
	check (value in ('pending', 'accepted', 'waiting-client', 'in-way', 'completed', 'cancelled'));