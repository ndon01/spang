create table permissions
(
    id serial primary key,
    description varchar(255),
    name        varchar(255)
);

create table roles
(
    id serial primary key,
    description varchar(255),
    name        varchar(255)
);

create table role_permissions
(
    id serial primary key,
    role_id integer not null references roles(id) on delete cascade,
    permission_id integer not null references permissions(id) on delete cascade,
    primary key (role_id, permission_id)
);

