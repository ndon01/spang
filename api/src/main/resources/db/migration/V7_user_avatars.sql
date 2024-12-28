create table user_avatars
(
    user_id integer not null references users(id) on delete cascade,
    file_metadata_id uuid not null references file_metadata(id) on delete cascade
);
