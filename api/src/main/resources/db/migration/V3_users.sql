create table users
(
    id serial primary key,
    username text unique not null,
    authentication_profile_id integer references authentication_profiles(id),
);