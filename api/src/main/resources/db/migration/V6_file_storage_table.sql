create table file_metadata
(
    id uuid not null primary key,
    aws_file_bucket text,
    aws_file_key text,
    aws_file_link text,
    file_access_policy text,
    file_name text not null,
    file_size bigint not null,
    file_type text not null,
    file_owner_id integer references users(id)
);
