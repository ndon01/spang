create table user_permissions
(
    id serial primary key,
    user_id integer not null references users(id) on delete cascade,
    permission_id integer not null references permissions(id) on delete cascade,
);

create table user_roles
(
    id serial primary key,
    user_id integer not null references users(id) on delete cascade,
    role_id integer not null references roles(id) on delete cascade,
);