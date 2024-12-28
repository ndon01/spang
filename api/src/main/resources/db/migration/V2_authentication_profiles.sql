create table authentication_profiles
(
    id serial primary key,
    google_id     text,
    password_hash text,
);